package com.repository.entity;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Finderlo on 2016/11/11.
 */
@Entity
@Table(name = "slog")
public class SlogEntity {
    private int logId;
    private String logType;
    private String operationId;
    private Timestamp logDate;
    private String logLevel;
    private String logTable;
    private String logInfo;
    private String logAnnonation;

    @Id
    @Column(name = "log_ID")
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    @Basic
    @Column(name = "log_type")
    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Basic
    @Column(name = "operation_ID")
    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    @Basic
    @Column(name = "log_date")
    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }

    @Basic
    @Column(name = "log_level")
    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @Basic
    @Column(name = "log_table")
    public String getLogTable() {
        return logTable;
    }

    public void setLogTable(String logTable) {
        this.logTable = logTable;
    }

    @Basic
    @Column(name = "log_info")
    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    @Basic
    @Column(name = "log_annonation")
    public String getLogAnnonation() {
        return logAnnonation;
    }

    public void setLogAnnonation(String logAnnonation) {
        this.logAnnonation = logAnnonation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SlogEntity entity = (SlogEntity) o;

        if (logId != 0 ? logId == (entity.logId) : entity.logId != 0) return false;
        if (logType != null ? !logType.equals(entity.logType) : entity.logType != null)
            return false;
        if (operationId != null ? !operationId.equals(entity.operationId) : entity.operationId != null)
            return false;
        if (logDate != null ? !logDate.equals(entity.logDate) : entity.logDate != null)
            return false;
        if (logLevel != null ? !logLevel.equals(entity.logLevel) : entity.logLevel != null)
            return false;
        if (logTable != null ? !logTable.equals(entity.logTable) : entity.logTable != null)
            return false;
        if (logInfo != null ? !logInfo.equals(entity.logInfo) : entity.logInfo != null)
            return false;
        if (logAnnonation != null ? !logAnnonation.equals(entity.logAnnonation) : entity.logAnnonation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logId != 0 ? logId : 0;
        result = 31 * result + (logType != null ? logType.hashCode() : 0);
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        result = 31 * result + (logDate != null ? logDate.hashCode() : 0);
        result = 31 * result + (logLevel != null ? logLevel.hashCode() : 0);
        result = 31 * result + (logTable != null ? logTable.hashCode() : 0);
        result = 31 * result + (logInfo != null ? logInfo.hashCode() : 0);
        result = 31 * result + (logAnnonation != null ? logAnnonation.hashCode() : 0);
        return result;
    }
}
