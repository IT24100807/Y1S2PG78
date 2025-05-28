package com.exam.result.model;

// Data Structure - Node for Linked Sort implementation
public class StudentResultNode {
    private Result result;
    private StudentResultNode next;

    public StudentResultNode(Result result) {
        this.result = result;
        this.next = null;
    }

    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }
    public StudentResultNode getNext() { return next; }
    public void setNext(StudentResultNode next) { this.next = next; }
}
