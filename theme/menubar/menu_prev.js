// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks

// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 1 //replace 0 with 1 to show the whole tree

HIGHLIGHT = 0

foldersTree = gFld("", "")

aux1 = insFld(foldersTree, gFld("<b>SEJ & LWR</b>", ""))
	aux2 = insFld(aux1, gFld("<b>Master</b>", ""))
		insDoc(aux2, gLnk("S", "SEJ", "MstSej.jsp"))
		insDoc(aux2, gLnk("S", "LWR", "MstLwr.jsp"))
	aux3 =	insFld(aux1, gFld("<b>Inspection</b>", ""))
		insDoc(aux3, gLnk("S", "SEJ", "SejInspection.jsp"))
		insDoc(aux3, gLnk("S", "LWR", "LwrInspection.jsp"))		

aux4 =	insFld(foldersTree, gFld("<b><nobr>Points & Crossing</nobr></b>", ""))
	aux5 = insFld(aux4, gFld("<b>Master</b>", ""))
		insDoc(aux5, gLnk("S", "<nobr>Points & Crossing</nobr>", "MstPointsAndCrossing.jsp"))
	//aux6 =	insFld(aux4, gFld("<b>Inspection</b>", ""))
		//insDoc(aux6, gLnk("S", "<nobr>Points & Crossing</nobr>", "PointsandCrossingInspection.jsp"))	
		
aux7 =	insFld(foldersTree, gFld("<b>Track Structure</b>", ""))
	aux8 = insFld(aux7, gFld("<b>Master</b>", ""))
		insDoc(aux8, gLnk("S","Rail", "MstRailDetails.jsp"))
		insDoc(aux8, gLnk("S","Rail Joint", "MstRailJointDetails.jsp"))
		insDoc(aux8, gLnk("S","Sleeper", "MstSleeperDetails.jsp"))
		insDoc(aux8, gLnk("S","Fastening", "MstFasteningDetails.jsp"))


aux9 =	insFld(foldersTree, gFld("<b>Job Card</b>", ""))
	insDoc(aux9, gLnk("S", "Generate", "JobCard.jsp"))
	//insDoc(aux6, gLnk("S", "Compliance", "JobCardCompliance.jsp"))
	//insDoc(aux6, gLnk("S", "Report", "JobCardReport.jsp"))

aux10 = insFld(foldersTree, gFld("<b><nobr>Master Data Report</nobr></b>", ""))
	insDoc(aux10, gLnk("S","Report", "MastersReportParameter.jsp"))
	
insDoc(foldersTree, gLnk("S","<b>USFD</b>", "RediredctURLController?moduleName=U"))
insDoc(foldersTree, gLnk("S","<b>RWF</b>", "RediredctURLController?moduleName=R"))
insDoc(foldersTree, gLnk("S","<b>TRC</b>", "RediredctURLController?moduleName=T"))	
insDoc(foldersTree, gLnk("S","LogOff", "LogoffController"))	
