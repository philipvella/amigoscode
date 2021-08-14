package com.example.demo.student;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student postStudent(Student student) {
        final Optional<Student> dbStudent = studentRepository.findStudentByEmail(student.getEmail());
        return dbStudent
                .map(value -> studentRepository.getById(value.getId()))
                .orElseGet(() -> studentRepository.save(student));
    }

    public void deleteStudent(Long studentId) {
        final boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new NoSuchElementException("Student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }
}
