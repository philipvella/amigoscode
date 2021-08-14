package com.example.demo.student;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.base.Strings;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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
            throw studentNotFound(studentId);
        }
        studentRepository.deleteById(studentId);
    }

    private NoSuchElementException studentNotFound(Long studentId) {
        return new NoSuchElementException("Student with id " + studentId + " does not exist");
    }

    @Transactional
    public void putStudent(Long studentId, String name, String email) throws IllegalStateException {
        final Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> studentNotFound(studentId));
        if (!Strings.isNullOrEmpty(name) && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (!Strings.isNullOrEmpty(email) && !Objects.equals(student.getEmail(), email)) {
            if (studentRepository.findStudentByEmail(email).isPresent()) {
                throw new IllegalStateException("Email " + email + " already taken");
            }
            student.setEmail(email);
        }
    }
}
