package org.example;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> list;

    public StudentManager() {
        this.list = new ArrayList<>();
    }

    public List<Student> getList() {
        return list;
    }

    // Thêm sinh viên
    public boolean addStudent(Student s) {
        // Kiểm tra trùng Mã SV
        for (Student item : list) {
            if (item.getId().equalsIgnoreCase(s.getId())) {
                return false;
            }
        }
        list.add(s);
        return true;
    }

    // Sửa sinh viên theo chỉ mục (index) trong danh sách
    public void updateStudent(int index, String name, double gpa) {
        if (index >= 0 && index < list.size()) {
            Student s = list.get(index);
            s.setName(name);
            s.setGpa(gpa);
        }
    }

    // Xóa sinh viên
    public void deleteStudent(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }
}