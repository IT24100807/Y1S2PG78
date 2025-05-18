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
}
