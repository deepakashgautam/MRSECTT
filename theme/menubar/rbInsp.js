// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks

// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree

HIGHLIGHT = 0

foldersTree = gFld("", "")

aux2= insFld(foldersTree, gFld("<b>Inspection</b>", ""))
	insDoc(aux2, gLnk("S", "SEJ", "SejInspection.jsp"))
	insDoc(aux2, gLnk("S", "LWR", "LwrInspection.jsp"))
	insDoc(aux2, gLnk("S", "Pt & Xing", "PxingInspection.jsp"))
	insDoc(aux2, gLnk("S", "Curve", "CurveInspection.jsp"))
	insDoc(aux2, gLnk("S", "<nobr>Weld Fracture</nobr>", "WeldFractureInspection_Servlet"))
	insDoc(aux2, gLnk("S", "<nobr>Trolley Inspection</nobr>", "TrolleyInsp.jsp?insptype=T"))
	insDoc(aux2, gLnk("S", "<nobr>Foot Plate Inspection</nobr>", "TrolleyInsp.jsp?insptype=F"))
	

aux4 = insFld(foldersTree, gFld("<b><nobr>Query</nobr></b>", ""))
	insDoc(aux4, gLnk("S","Search", "AssetInspParameter.jsp"))
	insDoc(aux4, gLnk("S", "<nobr>Job Card Status</nobr>", "JobCardReport.jsp"))
	
aux5 = insFld(foldersTree, gFld("<b><nobr>Report</nobr></b>", ""))
	insDoc(aux5, gLnk("S","<nobr>Track Network</nobr>", "MastersReportParameter.jsp"))
	insDoc(aux5, gLnk("S","<nobr>Asset Register</nobr>", "AssetRegisterParameter.jsp"))
	insDoc(aux5, gLnk("S", "<nobr>Inspection</nobr>", "InspectionRegisterParameters.jsp"))
	insDoc(aux5, gLnk("S", "<nobr>Inspection Register</nobr>", "StandardReport.jsp"))
	insDoc(aux5, gLnk("S","Track Diagram", "TrackDiagramAjaxNew.jsp"))
	insDoc(aux5, gLnk("S","TRC", "ReportServlet"))
	insDoc(aux5, gLnk("S","USFD", "ReportServletUSFD"))

insDoc(foldersTree, gLnk("S","Change Password", "UpdateProfile.jsp"))	
insDoc(foldersTree, gLnk("S","LogOff", "LogoffController"))	


