package person.louchen.snowflake.shadow.uid.worker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import person.louchen.snowflake.shadow.uid.worker.entity.WorkerNodeEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by louchen on 2017/5/31.
 */
@Repository
public class WorkerNodeDAOImpl implements WorkerNodeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerNodeDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public WorkerNodeEntity getWorkerNodeByHostPort(String host, String port) {
        List<WorkerNodeEntity> entities = new ArrayList<>();

        String sqlStr = "select a.ID,\n" +
                "a.HOST_NAME,\n" +
                "a.PORT,\n" +
                "a.TYPE,\n" +
                "a.LAUNCH_DATE,\n" +
                "a.MODIFIED,\n" +
                "a.CREATED\n" +
                "from WORKER_NODE a\n" +
                "where a.HOST_NAME = ? and a.PORT = ?";

        jdbcTemplate.query(sqlStr, new Object[]{host, port}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                WorkerNodeEntity entity = new WorkerNodeEntity();
                entity.setId(rs.getLong("ID"));
                entity.setHostName(rs.getString("HOST_NAME"));
                entity.setPort(rs.getString("PORT"));
                entity.setType(rs.getInt("TYPE"));
                entity.setLaunchDate(rs.getTimestamp("LAUNCH_DATE"));
                entity.setModified(rs.getTimestamp("MODIFIED"));
                entity.setCreated(rs.getTimestamp("CREATED"));
                entities.add(entity);
            }
        });

        WorkerNodeEntity entity = entities != null && entities.stream().count() > 0 ? entities.get(0) : null;
        return entity;

    }

    @Override
    public void addWorkerNode(WorkerNodeEntity workerNodeEntity) {
        WorkerNodeEntity old = getWorkerNodeByHostPort(workerNodeEntity.getHostName(), workerNodeEntity.getPort());
        if(old==null){
            insert(workerNodeEntity);
        }else{
            workerNodeEntity.setId(old.getId());
            update(workerNodeEntity);
        }

    }

    private void insert(WorkerNodeEntity workerNodeEntity){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlStr = "insert into WORKER_NODE(HOST_NAME, PORT, TYPE, LAUNCH_DATE, MODIFIED, CREATED) values(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement(sqlStr, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, workerNodeEntity.getHostName());
                ps.setString(2, workerNodeEntity.getPort());
                ps.setInt(3, workerNodeEntity.getType());
                ps.setDate(4, new java.sql.Date(workerNodeEntity.getLaunchDate().getTime()));
                ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
                ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));

                return ps;
            }
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        workerNodeEntity.setId(generatedId);
    }

    private void update(WorkerNodeEntity workerNodeEntity){
        String sqlStr = "update WORKER_NODE set LAUNCH_DATE = ?,MODIFIED = ?  where id = ?";
        jdbcTemplate.update(sqlStr, new Object[]{new java.sql.Date(workerNodeEntity.getLaunchDate().getTime()),new java.sql.Timestamp(new Date().getTime()),workerNodeEntity.getId()});
    }

}
