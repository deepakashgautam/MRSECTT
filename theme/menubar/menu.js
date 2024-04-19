// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks

// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree

HIGHLIGHT = 0

foldersTree = gFld("", "")

aux1 = insFld(foldersTree, gFld("<b><nobr>Assets</nobr></b>", ""))
	insDoc(aux1, gLnk("S","Rails", "MstRailDetail.jsp"))
	insDoc(aux1, gLnk("S","Rail Joints", "MstRailJointDetails.jsp"))
	insDoc(aux1, gLnk("S","Weld", "CTR_Weld_Register_new_Servlet"))	
	insDoc(aux1, gLnk("S","Sleepers", "MstSleeperDetails.jsp"))
	insDoc(aux1, gLnk("S","Fastening", "MstFastening.jsp"))	
	insDoc(aux1, gLnk("S", "SEJ", "MstSej.jsp"))
	insDoc(aux1, gLnk("S", "LWR", "MstLwr.jsp"))
	insDoc(aux1, gLnk("S", "Pt & Xing", "MstPointsAndCrossing.jsp"))
	insDoc(aux1, gLnk("S", "Curve", "MstCurve.jsp"))
	
	insDoc(aux1, gLnk("S","Speed Restriction", "MstSpeed.jsp?restype=T"))
	insDoc(aux1, gLnk("S","Ballast", "MstBallast.jsp"))
	insDoc(aux1, gLnk("S","Formation", "MstFormationDtl.jsp"))
	
	insDoc(aux1, gLnk("S","<nobr>Protection Work</nobr>", "MstProtectionDtl.jsp"))
	insDoc(aux1, gLnk("S","<nobr>Drainage System</nobr>", "MstDrainageDtl.jsp"))
	insDoc(aux1, gLnk("S","<nobr>Erosion Control Measures</nobr>", "MstErosionDtl.jsp"))
	insDoc(aux1, gLnk("S","<nobr>Weak Formation</nobr>", "WeakFormationDtl.jsp"))
	insDoc(aux1, gLnk("S","<nobr>Formation Treatment</nobr>", "FormationTreatDtl.jsp"))
	insDoc(aux1, gLnk("S","<nobr>Level Crossing</nobr>", "MstLevelXing.jsp"))
	insDoc(aux1, gLnk("S","<nobr>Bridge</nobr>", "BridgeMaster.jsp"))
	insDoc(aux1, gLnk("S", "<nobr>Land Boundary </nobr>", "landBoundary.jsp"))
	insDoc(aux1, gLnk("S", "<nobr>ODC</nobr>", "odc.jsp"))
	
aux2= insFld(foldersTree, gFld("<b>Inspection</b>", ""))
	insDoc(aux2, gLnk("S", "SEJ", "SejInspection.jsp"))
	insDoc(aux2, gLnk("S", "LWR", "LwrInspection.jsp"))
	insDoc(aux2, gLnk("S", "Pt & Xing", "PxingInspection.jsp"))
	insDoc(aux2, gLnk("S", "Curve", "CurveInspection.jsp"))
	insDoc(aux2, gLnk("S", "<nobr>Weld Fracture</nobr>", "WeldFractureInspection_Servlet"))
	insDoc(aux2, gLnk("S", "<nobr>Level Crossing</nobr>", "LevelXingInspection.jsp"))
	insDoc(aux2, gLnk("S", "<nobr>Push Trolley</nobr>", "TrolleyInsp.jsp?insptype=T"))
	insDoc(aux2, gLnk("S", "<nobr>Foot Plate</nobr>", "TrolleyInsp.jsp?insptype=F"))
	insDoc(aux2, gLnk("S", "<nobr>Rail</nobr>", "rail_inspection.jsp"))
	insDoc(aux2, gLnk("S", "<nobr>Ballast</nobr>", "ballastInspection.jsp"))
	insDoc(aux2, gLnk("S", "<nobr>Fastening</nobr>", "fasteningInspection.jsp"))


aux3= insFld(foldersTree, gFld("<b>Job Card</b>", ""))
	insDoc(aux3, gLnk("S", "Generate", "JobCard.jsp"))
	insDoc(aux3, gLnk("S", "Compliance", "JobCardCompliance.jsp"))

aux4 = insFld(foldersTree, gFld("<b><nobr>Query</nobr></b>", ""))
	insDoc(aux4, gLnk("S","Search", "AssetInspParameter.jsp"))
	insDoc(aux4, gLnk("S", "<nobr>Job Card Status</nobr>", "JobCardReport.jsp"))
	
	
aux5 = insFld(foldersTree, gFld("<b><nobr>Report</nobr></b>", ""))
	insDoc(aux5, gLnk("S","<nobr>Track Network</nobr>", "MastersReportParameter.jsp"))
	insDoc(aux5, gLnk("S","<nobr>Asset Register</nobr>", "AssetRegisterParameter.jsp"))
	insDoc(aux5, gLnk("S", "<nobr>Inspection</nobr>", "InspectionRegisterParameters.jsp"))
	insDoc(aux5, gLnk("S", "<nobr>Inspection Register</nobr>", "StandardReport.jsp"))
	insDoc(aux5, gLnk("S","Weld Fracture", "WeldFractureReport_Servlet"))
	insDoc(aux5, gLnk("S","Track Diagram", "TrackDiagramAjaxNew.jsp"))
	insDoc(aux5, gLnk("S","TRC", "ReportServlet"))
	insDoc(aux5, gLnk("S","USFD", "ReportServletUSFD"))

	
		
	

	




