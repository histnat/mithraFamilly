package net.mithra.familly.utils;

import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertNotNull;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.Conversion;
import net.mithra.familly.log.model.DBLogger;
import net.mithra.familly.wf.dao.WorkFlowDao;
import net.mithra.familly.wf.exception.WorkflowException;
import net.mithra.familly.wf.xmodel.Sequence;
import net.mithra.familly.wf.xmodel.Workflow;

public class TestHelperWF extends TestHelper {

	@Autowired
	WorkFlowDao workFlowDao;

	protected DBLogger getDBLogger(ResourceTestPath rtp, String idSequence) {
		Workflow wf=null;
		try {
			wf = workFlowDao.loadWorkflow(rtp.getWf());
		} catch (WorkflowException e) {
			fail(e.getMessage());
			return null;
		}
		Sequence s = getSequence(rtp,idSequence, wf);
		Conversion c = getConversion(rtp,s);
		DBLogger dbl=super.getDBLogger(c);
		dbl.setWorkflow(wf);
		return dbl;
	}

	private Conversion getConversion(ResourceTestPath rtp, Sequence sequence) {
		String folderin = getFilePath(rtp.getXml());
		Conversion conversion = new Conversion();
		conversion.setFolderIn(folderin);
		conversion.setFolderOut(System.getProperty("user.dir") + File.separator + rtp.toString());
		conversion.setStartTime(1443351300000L);
		conversion.setWorkFlowName("Workflow de test ");
		conversion.setWorkFlowSequence(sequence.getId().toString());
			try {
				conversion = dbConversionService.getOrSave(conversion);
			} catch (DBNONullException | DBNOUniqueException e) {
				fail(e.getMessage());
			}
		

		return conversion;
	}

	protected Sequence getSequence(ResourceTestPath rtp, String idSequence, Workflow wf) {
		String workFlowName = rtp.getWf();
		if (wf == null) {
			try {
				wf = workFlowDao.loadWorkflow(workFlowName);
			} catch (WorkflowException e) {
				fail(e.getMessage());
				return null;
			}
		}
		Sequence s = null;
		try {
			s = workFlowDao.getSequence(wf, idSequence);
		} catch (WorkflowException e) {
			fail(e.getMessage());
		}
		assertNotNull(s);
		return s;
	}

	public WorkFlowDao getWorkFlowDao() {
		return workFlowDao;
	}

	public void setWorkFlowDao(WorkFlowDao workFlowDao) {
		this.workFlowDao = workFlowDao;
	}

}
