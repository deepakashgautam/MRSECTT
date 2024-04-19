USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree
HIGHLIGHT = 0
foldersTree = gFld("", "")
//insDoc(foldersTree, gLnk("S","Route", "MstRoute.jsp"))
//insDoc(foldersTree, gLnk("S","Line", "MstLine.jsp"))
insDoc(foldersTree, gLnk("S","Station", "MstStation.jsp"))
//insDoc(foldersTree, gLnk("S","Supplier", "MstSupplier.jsp"))
insDoc(foldersTree, gLnk("S","Role", "MstRole.jsp"))
insDoc(foldersTree, gLnk("S","<nobr>Create User</nobr>", "MstLogin.jsp"))
//insDoc(foldersTree, gLnk("S","Section", "MstSection.jsp"))
//insDoc(foldersTree, gLnk("S","Sub Section", "MstSubSection.jsp"))
insDoc(foldersTree, gLnk("S","Section", "MstSectionExtra.jsp"))
insDoc(foldersTree, gLnk("S","Gang", "MstGang.jsp"))
insDoc(foldersTree, gLnk("S","Loop Line", "MstLoopLine.jsp"))
insDoc(foldersTree, gLnk("S","Jurisdiction", "Jurisdiction.jsp"))
insDoc(foldersTree, gLnk("S","GMT", "MstGMTDetails.jsp"))
insDoc(foldersTree, gLnk("S","<nobr>Critical Location</nobr>", "MstCriticalLocation.jsp"))
insDoc(foldersTree, gLnk("S","Gradient", "MstGradient.jsp"))
insDoc(foldersTree, gLnk("S","Speed Restriction", "MstSpeed.jsp?restype=P"))
insDoc(foldersTree, gLnk("S","Edit Threshold", "MstEditThreshold.jsp"))
insDoc(foldersTree, gLnk("S","Inspection Schedule <BR> (Under Development)", "Home.jsp"))
//insDoc(foldersTree, gLnk("S","Trc Peak Master", "PeakMasterServlet"))
insDoc(foldersTree, gLnk("S", "Usfd Team", "TeamMst_Servlet"))
//insDoc(aux1, gLnk("S", "Usfd Machine Make", "Ctus_mc_mk_mst_r_Servlet"))
insDoc(foldersTree, gLnk("S", "Usfd Machine", "McMst_Servlet"))
insDoc(foldersTree, gLnk("S", "EQKM Entry", "MstEqKmServlet"))

insDoc(foldersTree, gLnk("S","<nobr>Short/Long Kilometer</nobr>", "MstKmMet.jsp"))

insDoc(foldersTree, gLnk("S", "<nobr>TRC Extraction</nobr>", "TrcExtraction2500"))
insDoc(foldersTree, gLnk("S", "<nobr>TRC Bifurcation</nobr>", "BifurcationServlet"))
	
//insDoc(foldersTree, gLnk("S","Track Diagram", "TrackDiagramAjax.jsp"))

//insDoc(foldersTree, gLnk("S","Master Codes", "MasterCodeMaintenance.jsp"))


aux1 = insFld(foldersTree, gFld("<b><nobr>Report</nobr></b>", ""))
	insDoc(aux1, gLnk("S","<nobr>Track Netwrok</nobr>", "MastersReportParameter.jsp"))
	insDoc(aux1, gLnk("S","Track Diagram", "TrackDiagramAjaxNew.jsp"))

//insDoc(foldersTree, gLnk("S","USFD", "http://203.176.113.28:9080/USFD/Validate"))
//insDoc(foldersTree, gLnk("S","RWF", "http://203.176.113.28:9080/RWF/CTR_WELDER_MASTER_Servlet"))
//insDoc(foldersTree, gLnk("S","TRC", "http://203.176.113.28:9080/TRC/TrcExtraction7965"))

insDoc(foldersTree, gLnk("S","User Profile", "UpdateProfile.jsp"))
//insDoc(foldersTree, gLnk("S","LogOff", "LogoffController"))	
aux2 = insFld(foldersTree, gFld("<b><nobr>Message Center</nobr></b>", ""))
	insDoc(aux1, gLnk("S","<nobr>Compose/New</nobr>", "messagecompose.jsp"))
	insDoc(aux1, gLnk("S","Inbox", "messageinbox.jsp"))

