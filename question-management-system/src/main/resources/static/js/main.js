const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', function() {
    // Initialize modals
    const addQuestionModal = new bootstrap.Modal(document.getElementById('addQuestionModal'));
    const editQuestionModal = new bootstrap.Modal(document.getElementById('editQuestionModal'));

    // Add question type change handler
    document.getElementById('questionType').addEventListener('change', function() {
        updateOptionsContainer(this.value, 'optionsContainer');
    });

    // Initialize section visibility
    showSection('questions');
    
    // Add section navigation handlers
    document.querySelectorAll('[data-section]').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const section = e.currentTarget.dataset.section;
            showSection(section);
        });
    });
    
    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Load questions on page load
    loadQuestions();

    // Handle question type change
    document.getElementById('questionType').addEventListener('change', function() {
        updateDynamicFields(this.value);
    });

    // Handle form submission
    document.getElementById('questionForm').addEventListener('submit', function(e) {
        e.preventDefault();
        submitQuestion();
    });

    // Handle search form
    document.getElementById('searchForm').addEventListener('submit', function(e) {
        e.preventDefault();
        searchQuestions();
    });

    // Handle bulk upload
    document.getElementById('uploadForm').addEventListener('submit', function(e) {
        e.preventDefault();
        uploadFile();
    });
});

function updateDynamicFields(questionType) {
    const container = document.getElementById('dynamicFields');
    container.innerHTML = '';

    switch(questionType) {
        case 'MCQ':
            container.innerHTML = `
                <div class="mb-3">
                    <label class="form-label">Options</label>
                    <div id="optionsContainer">
                        <div class="input-group mb-2">
                            <input type="text" class="form-control" name="options[]" required>
                            <button type="button" class="btn btn-outline-secondary" onclick="addOption()">+</button>
                        </div>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Correct Answer</label>
                    <input type="text" class="form-control" id="correctAnswer" required>
                </div>
            `;
            break;
        case 'TRUE_FALSE':
            container.innerHTML = `
                <div class="mb-3">
                    <label class="form-label">Correct Answer</label>
                    <select class="form-select" id="correctAnswer" required>
                        <option value="true">True</option>
                        <option value="false">False</option>
                    </select>
                </div>
            `;
            break;
        case 'ESSAY':
            container.innerHTML = `
                <div class="mb-3">
                    <label class="form-label">Word Limit</label>
                    <input type="number" class="form-control" id="wordLimit" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Sample Answer</label>
                    <textarea class="form-control" id="sampleAnswer" rows="3" required></textarea>
                </div>
            `;
            break;
    }
}

function addOption() {
    const container = document.getElementById('optionsContainer');
    const div = document.createElement('div');
    div.className = 'input-group mb-2';
    div.innerHTML = `
        <input type="text" class="form-control" name="options[]" required>
        <button type="button" class="btn btn-outline-danger" onclick="this.parentElement.remove()">-</button>
    `;
    container.appendChild(div);
}

let editingQuestionId = null;

async function submitQuestion() {
    const questionType = document.getElementById('questionType').value;
    const questionData = {
        questionType: questionType,
        questionText: document.getElementById('questionText').value,
        topic: document.getElementById('topic').value,
        marks: parseInt(document.getElementById('marks').value),
        difficulty: document.getElementById('difficulty').value,
        examId: document.getElementById('examId').value
    };

    // Add type-specific fields
    let finalData;
    switch(questionType) {
        case 'MCQ':
            const options = Array.from(document.getElementsByName('options[]')).map(input => input.value);
            finalData = {
                ...questionData,
                options: options,
                correctAnswer: document.getElementById('correctAnswer').value
            };
            break;
        case 'TRUE_FALSE':
            finalData = {
                ...questionData,
                correctAnswer: document.getElementById('correctAnswer').value === 'true'
            };
            break;
        case 'ESSAY':
            finalData = {
                ...questionData,
                wordLimit: parseInt(document.getElementById('wordLimit').value),
                sampleAnswer: document.getElementById('sampleAnswer').value
            };
            break;
    }

    try {
        const url = editingQuestionId ? 
            `${API_BASE_URL}/api/questions/${editingQuestionId}` : 
            `${API_BASE_URL}/api/questions`;

        const response = await fetch(url, {
            method: editingQuestionId ? 'PUT' : 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(finalData)
        });

        if (response.ok) {
            alert(editingQuestionId ? 'Question updated successfully!' : 'Question added successfully!');
            editingQuestionId = null;
            document.getElementById('questionForm').reset();
            loadQuestions();
        } else {
            alert(editingQuestionId ? 'Error updating question' : 'Error adding question');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error adding question');
    }
}

async function loadQuestions() {
    try {
        const response = await fetch(`${API_BASE_URL}/api/questions`, {
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const questions = await response.json();
        if (!Array.isArray(questions)) {
            throw new Error('Expected an array of questions');
        }
        
        displayQuestions(questions);
    } catch (error) {
        console.error('Error:', error);
        const container = document.getElementById('questionsList');
        container.innerHTML = `
            <tr>
                <td colspan="7" class="text-center text-danger">
                    Error loading questions: ${error.message}
                </td>
            </tr>
        `;
    }
}

function showSection(sectionId) {
    // Hide all sections
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    
    // Show selected section
    const selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.classList.add('active');
    }
    
    // Update active state in sidebar
    document.querySelectorAll('[data-section]').forEach(link => {
        link.classList.remove('active');
        if (link.dataset.section === sectionId) {
            link.classList.add('active');
        }
    });
}

function clearSearch() {
    document.getElementById('searchTopic').value = '';
    document.getElementById('searchDifficulty').value = '';
    loadQuestions();
}

function exportQuestions() {
    // TODO: Implement export functionality
    alert('Export functionality coming soon!');
}

function displayQuestions(questions) {
    const container = document.getElementById('questionsList');
    container.innerHTML = '';

    if (questions.length === 0) {
        container.innerHTML = `
            <tr>
                <td colspan="7" class="text-center text-muted py-4">
                    <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                    No questions found
                </td>
            </tr>
        `;
        return;
    }

    questions.forEach((question, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${index + 1}</td>
            <td>
                <div class="text-truncate" style="max-width: 300px;" title="${question.questionText}">
                    ${question.questionText}
                </div>
            </td>
                        <td><span class="badge bg-${getDifficultyColor(question.difficulty)}">${question.difficulty}</span></td>

            <td><span class="badge bg-${getTypeColor(question.questionType)}">${formatType(question.questionType)}</span></td>
            <td>${question.topic || '-'}</td>
            <td>${question.marks}</td>
            <td class="text-end">
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editQuestion(${JSON.stringify(question).replace(/"/g, "'")}, '${question.questionType}')"style="margin: 3px">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteQuestion(${question.id})"style="margin-right: 3px">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        container.appendChild(tr);
    });
}

async function searchQuestions() {
    const topic = document.getElementById('searchTopic').value;
    const type = document.getElementById('searchType').value;
    const difficulty = document.getElementById('searchDifficulty').value;

    try {
        const params = new URLSearchParams();
        if (topic) params.append('topic', topic);
        if (type) params.append('type', type);
        if (difficulty) params.append('difficulty', difficulty);

        const response = await fetch(`${API_BASE_URL}/api/questions/search?${params}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const questions = await response.json();
        displayQuestions(questions);

        // Show no results message if needed
        if (questions.length === 0) {
            document.getElementById('questionsList').innerHTML = `
                <tr>
                    <td colspan="6" class="text-center py-4">
                        <div class="text-muted">
                            <i class="bi bi-search fs-2 d-block mb-2"></i>
                            <p class="mb-0">No questions found</p>
                            <small class="text-muted mt-1">
                                ${topic ? `Topic: ${topic}` : ''}
                                ${type ? `Type: ${type}` : ''}
                                ${difficulty ? `Difficulty: ${difficulty}` : ''}
                            </small>
                        </div>
                    </td>
                </tr>
            `;
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('questionsList').innerHTML = `
            <tr>
                <td colspan="6" class="text-center text-danger py-4">
                    <i class="bi bi-exclamation-circle fs-2 d-block mb-2"></i>
                    <p class="mb-0">Error searching questions: ${error.message}</p>
                </td>
            </tr>
        `;
    }
}

function formatType(type) {
    const types = {
        'MCQ': 'Multiple Choice',
        'TRUE_FALSE': 'True/False',
        'ESSAY': 'Essay'
    };
    return types[type] || type;
}

function getTypeColor(type) {
    const colors = {
        'MCQ': 'info',
        'TRUE_FALSE': 'success',
        'ESSAY': 'primary'
    };
    return colors[type] || 'secondary';
}

function getDifficultyColor(difficulty) {
    const colors = {
        'EASY': 'success',
        'MEDIUM': 'warning',
        'HARD': 'danger'
    };
    return colors[difficulty] || 'secondary';
}

function editQuestion(question, type) {
    // Set values in edit modal
    document.getElementById('editQuestionId').value = question.id;
    document.getElementById('editQuestionText').value = question.questionText;
    document.getElementById('editTopic').value = question.topic;
    document.getElementById('editDifficulty').value = question.difficulty;
    document.getElementById('editMarks').value = question.marks;
    document.getElementById('editQuestionType').value = type;

    // Handle MCQ options if present
    if (type === 'MCQ' && question.options) {
        const optionsContainer = document.getElementById('editOptionsContainer');
        optionsContainer.innerHTML = `
            <div class="mb-3">
                <label class="form-label">Options</label>
                <div class="mcq-options">
                    ${question.options.map((option, index) => `
                        <div class="input-group mb-2">
                            <span class="input-group-text">${index + 1}</span>
                            <input type="text" class="form-control" name="editOption${index + 1}" value="${option}" required>
                            ${index === 0 ? '<span class="input-group-text text-success">Correct</span>' : ''}
                        </div>
                    `).join('')}
                </div>
            </div>
        `;
    } else {
        document.getElementById('editOptionsContainer').innerHTML = '';
    }

    // Show edit modal
    const editModal = new bootstrap.Modal(document.getElementById('editQuestionModal'));
    editModal.show();
}

async function updateQuestion() {
    const form = document.getElementById('editQuestionForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const questionId = document.getElementById('editQuestionId').value;
    const questionData = {
        id: questionId,
        questionText: document.getElementById('editQuestionText').value,
        topic: document.getElementById('editTopic').value,
        difficulty: document.getElementById('editDifficulty').value,
        marks: parseInt(document.getElementById('editMarks').value),
        questionType: document.getElementById('editQuestionType').value
    };

    // Add options for MCQ questions
    if (questionData.questionType === 'MCQ') {
        const options = [];
        document.querySelectorAll('#editOptionsContainer input[type="text"]').forEach(input => {
            options.push(input.value);
        });
        if (options.length === 0) {
            alert('Please add at least one option for MCQ question');
            return;
        }
        questionData.options = options;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/questions/${questionId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(questionData)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Success:', result);
        
        // Close modal and refresh questions
        const modal = bootstrap.Modal.getInstance(document.getElementById('editQuestionModal'));
        modal.hide();
        clearForm();
        loadQuestions();
    } catch (error) {
        console.error('Error:', error);
        alert('Error updating question: ' + error.message);
    }
}

function clearForm() {
    // Clear add question form
    const addForm = document.getElementById('questionForm');
    if (addForm) {
        addForm.reset();
        document.getElementById('optionsContainer').innerHTML = '';
    }
    
    // Clear edit question form
    const editForm = document.getElementById('editQuestionForm');
    if (editForm) {
        editForm.reset();
        document.getElementById('editOptionsContainer').innerHTML = '';
    }
}

async function deleteQuestion(id) {
    if (confirm('Are you sure you want to delete this question?')) {
        try {
            const response = await fetch(`${API_BASE_URL}/api/questions/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            loadQuestions();
        } catch (error) {
            console.error('Error:', error);
            alert('Error deleting question');
        }
    }
}

// ... (rest of the code remains the same)

async function submitQuestion() {
    const form = document.getElementById('questionForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const questionData = {
        questionText: document.getElementById('questionText').value,
        topic: document.getElementById('topic').value,
        difficulty: document.getElementById('difficulty').value,
        marks: parseInt(document.getElementById('marks').value),
        questionType: document.getElementById('questionType').value
    };

    // Add options for MCQ questions
    if (questionData.questionType === 'MCQ') {
        const options = [];
        document.querySelectorAll('#optionsContainer input[type="text"]').forEach(input => {
            options.push(input.value);
        });
        if (options.length === 0) {
            alert('Please add at least one option for MCQ question');
            return;
        }
        questionData.options = options;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/questions`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(questionData)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Success:', result);
        
        // Close modal and refresh questions
        const modal = bootstrap.Modal.getInstance(document.getElementById('addQuestionModal'));
        modal.hide();
        clearForm();
        loadQuestions();
    } catch (error) {
        console.error('Error:', error);
        alert('Error adding question: ' + error.message);
    }
}
