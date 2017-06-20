package pl.com.bottega.dms.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBCleaner {

    @Autowired
    private JdbcTemplate template;

    private final String[] tables = {"Document"};

    public void clean() {
        for (String table : tables)
            template.execute("DELETE FROM " + table);
    }
}
