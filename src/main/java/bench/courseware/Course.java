package bench.courseware;

import java.util.HashMap;

import bench.Tables;

class Course {
    int id;
    String name;
    String department;

    Course(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    Course(HashMap<String, Object> row) {
        this.id = (int) row.get("id");
        this.name = (String) row.get("name");
        this.department = (String) row.get("department");
    }

    String getKey() {
        return Tables.encodeKey("course", new int[] { id });
    }

    String getRow() {
        HashMap<String, Object> row = new HashMap<>();

        row.put("id", id);
        row.put("name", name);
        row.put("department", department);

        return Tables.encodeTable(row);
    }
}
