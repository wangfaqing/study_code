//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.utils.methodLog.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.common.utils.methodLog.domain.TerminalInfoRecord;
import com.common.utils.methodLog.executeBuffer.FileWriteExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminalRecordBackupListener implements ExceptionListener<TerminalInfoRecord> {
    private static final Logger logger = LoggerFactory.getLogger(TerminalRecordBackupListener.class);
    private FileWriteExecutor fileWriteExecutor;

    public TerminalRecordBackupListener() {
    }

    public void onException(List<TerminalInfoRecord> records) {
        if (records != null && !records.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("onException, backup [{}] game records.", records.size());
            }

            List<String> sqls = new ArrayList();
            Iterator i$ = records.iterator();

            while(i$.hasNext()) {
                TerminalInfoRecord terminal = (TerminalInfoRecord)i$.next();
                sqls.add(terminal.toInsertSql());
            }

            this.fileWriteExecutor.execute(sqls);
        }

    }

    public void setFileWriteExecutor(FileWriteExecutor fileWriteExecutor) {
        this.fileWriteExecutor = fileWriteExecutor;
    }
}
