// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks

// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 1 //replace 0 with 1 to show the whole tree

HIGHLIGHT = 0

foldersTree = gFld("", "")

aux1 = insFld(foldersTree, gFld("<b><nobr>Railway Board</nobr></b>", ""))
	insDoc(aux1, gLnk("S", "Route", "MstRoute.jsp"))
	insDoc(aux1, gLnk("S", "Line", "MstLine.jsp"))
	insDoc(aux1, gLnk("S", "Station", "MstStation.jsp"))	
	insDoc(aux1, gLnk("S", "Supplier", "MstSupplier.jsp"))
	insDoc(aux1, gLnk("S", "Jurisdiction", "Jurisdiction.jsp"))

aux2 = insFld(foldersTree, gFld("<b>Division</b>", ""))
	insDoc(aux2, gLnk("S", "Role", "MstRole.jsp"))
	insDoc(aux2, gLnk("S", "<nobr>Create User</nobr>", "MstLogin.jsp"))
	insDoc(aux2, gLnk("S", "Section", "MstSection.jsp"))
	insDoc(aux2, gLnk("S", "Sub Section", "MstSectionExtra.jsp"))
	insDoc(aux2, gLnk("S", "Gang", "MstGang.jsp"))
	insDoc(aux2, gLnk("S", "Loop Line", "MstLoopLine.jsp"))

aux3 = insFld(foldersTree, gFld("<b><nobr>General Master</nobr></b>", ""))

	insDoc(aux3, gLnk("S", "SEJ", "MstSej.jsp"))

aux4= insFld(foldersTree, gFld("<b>Inspection</b>", ""))
	insDoc(aux4, gLnk("S", "SEJ", "SejInspection.jsp"))

aux5= insFld(foldersTree, gFld("<b>Job Card</b>", ""))
	insDoc(aux5, gLnk("S", "Generate", "JobCard.jsp"))
	insDoc(aux5, gLnk("S", "Compliance", "JobCardCompliance.jsp"))
	insDoc(aux5, gLnk("S", "Report", "JobCardReport.jsp"))

aux6= insFld(foldersTree, gFld("<b>LogOff</b>", "LogoffController"))
