package bench.courseware;

import java.util.HashMap;

import bench.Tables;

class Student {
    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    Student(HashMap<String, Object> row) {
        this.id = (int) row.get("id");
        this.name = (String) row.get("name");
    }

    String getKey() {
        return Tables.encodeKey("student", new int[] { id });
    }

    String getRow() {
        HashMap<String, Object> row = new HashMap<>();

        row.put("id", id);
        row.put("name", name);

        return Tables.encodeTable(row);
    }
}
