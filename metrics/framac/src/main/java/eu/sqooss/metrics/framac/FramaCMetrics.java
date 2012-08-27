/*
 * Copyright 2008 - Organization for Free and Open Source Software,  
 *                  Athens, Greece.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

/*
** That copyright notice makes sense for code residing in the 
** main SQO-OSS repository. For the Skeleton plug-in only, the Copyright
** notice may be removed and replaced by a statement of your own
** with (compatible) license terms as you see fit; the Skeleton
** plug-in itself is insufficiently a creative work to be protected
** by Copyright.
*/

/* This is the package for this particular plug-in. Third-party
** applications will want a different package name, but it is
** *ESSENTIAL* that the package name contain the string '.metrics.'
** because this is how Alitheia Core discovers the metric plug-ins. 
*/
package eu.sqooss.metrics.framac;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/* These are imports of standard Alitheia core services and types.
** You are going to need these anyway; some others that you might
** need are the FDS and other Metric interfaces, as well as more
** DAO types from the database service.
*/
import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.abstractmetric.AbstractMetric;
import eu.sqooss.service.abstractmetric.MetricDecl;
import eu.sqooss.service.abstractmetric.MetricDeclarations;
import eu.sqooss.service.abstractmetric.Result;
import eu.sqooss.service.db.Metric;
import eu.sqooss.service.db.ProjectFile;
import eu.sqooss.service.db.ProjectFileMeasurement;
import eu.sqooss.service.fds.FDSService;
//import eu.sqooss.service.fds.OnDiskCheckout;

/**
 * Implementation of the FRAMA-C driver plug-in. It should be activated in ProjectFile, 
 * ProjectDirectory or Project scope. The implementation currently supports only ProjectFile.  
 */ 
@MetricDeclarations(metrics= {
	@MetricDecl(mnemonic="FramaC.DoubleFree", activators={ProjectFile.class}, descr="FramaC: Double Free Vulnerability"),
	@MetricDecl(mnemonic="FramaC.FormatString", activators={ProjectFile.class}, descr="FramaC: Format String Vulnerability"),
	@MetricDecl(mnemonic="FramaC.SQLInjection", activators={ProjectFile.class}, descr="FramaC: SQL Injection Vulnerability"),
	@MetricDecl(mnemonic="FramaC.KernelTrust", activators={ProjectFile.class}, descr="FramaC: User Kernel Trust Error Vulnerability"),
	@MetricDecl(mnemonic="FramaC.XSS", activators={ProjectFile.class}, descr="FramaC: XSS Vulnerability")
})
public class FramaCMetrics extends AbstractMetric {
    
	static String FRAMAC_PATH = "";
	static String FRAMAC_CFG_PATH = "";
	static Map<String, String> configurations; //config -> params
	// Alternatively we could use the form config -> list<Params<name, value>>
	static {
        if (System.getProperty("framac.path") != null)
            FRAMAC_PATH = System.getProperty("framac.path");
        else
            FRAMAC_PATH = "frama-c";
        
        if (System.getProperty("framac.cfg.path") != null)
            FRAMAC_CFG_PATH = System.getProperty("framac.cfg.path");
        else
            FRAMAC_CFG_PATH = "/tmp";
        
        configurations = new HashMap<String, String>();
        configurations.put("Double_free", "-print-final");
        configurations.put("Format_string", "-print-final");
        configurations.put("SQL_Injection", "-print-final");
        configurations.put("User_kernel_Trust_error", "-print-final");
        configurations.put("XSS", "-print-final");
        
        exportConfigurations();
    }
	
	
	private static void exportConfigurations(){
		
	}
	
	// Holds the instance of the Alitheia core service
    private AlitheiaCore core;
	
	
    public FramaCMetrics(BundleContext bc) {
        super(bc);
        
        // Retrieve the instance of the Alitheia core service
        ServiceReference serviceRef = bc.getServiceReference(
                AlitheiaCore.class.getName());
        if (serviceRef != null)
            core = (AlitheiaCore) bc.getService(serviceRef);
        else
        	core = AlitheiaCore.getInstance();
    }

    public List<Result> getResult(ProjectFile a, Metric m) {
    	return getResult(a, ProjectFileMeasurement.class,
                m, Result.ResultType.STRING);
    }
    
    public void run(ProjectFile a) {
    	FDSService fds = core.getFDSService();

        //OnDiskCheckout odc = null;
        File file = fds.getFile(a);
        
        for(String config: configurations.keySet()){
        	//for each configuration build the command string, execute it and parse the results
        	
        	String cmd = buildCommand(file, config);
        	
        	try {
        	
	        	ProcessBuilder framacProcessBuilder = new ProcessBuilder(cmd);
	        	framacProcessBuilder.redirectErrorStream(true);
	        	framacProcessBuilder.directory(file.getParentFile());
	        	
	        	File resultFile = File.createTempFile("framac-result", null);
	        	
	        	int exitCode = getProcessExitCode(framacProcessBuilder.start(), resultFile.getPath());
	        	
	        	if(exitCode != 0) {
	        		log.warn(String.format("The external tool exited with code %i", exitCode));
	        		resultFile.delete();
	        		continue;
	        	}
	        	
	        	processResult(resultFile);
	        	resultFile.delete();
	        	
        	} catch(Exception ignored) {
        		continue;
        	}
        }
        
    }
    
    //TODO: mark as protected for base plugin
    private String buildCommand(File f, String config)
    {
    	return String.format("%s -config-file  %s/%s/default.cfg "
    			+ "-constr-config-file %s/%s/default_constr.cfg %s %s", 
    			FRAMAC_PATH, FRAMAC_CFG_PATH, config, FRAMAC_CFG_PATH, config, 
    			configurations.get(config), f.getPath());
    }
    
    private int getProcessExitCode(Process pr, String name) throws IOException {
        ProcessOutputReader outReader = new ProcessOutputReader(pr.getInputStream(), name);
        outReader.start();
        int retVal = -1;
        while (retVal == -1) {
            try {
                retVal = pr.waitFor();
            } catch (Exception ignored) {}
        }
        return retVal;
    }
    
    private void processResult(File file) {
    	
    }
    
}

// vi: ai nosi sw=4 ts=4 expandtab
