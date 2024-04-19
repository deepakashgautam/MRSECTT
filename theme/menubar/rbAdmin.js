USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree
HIGHLIGHT = 0
foldersTree = gFld("", "")
insDoc(foldersTree, gLnk("S","Route", "MstRoute.jsp"))
insDoc(foldersTree, gLnk("S","Line", "MstLine.jsp"))
insDoc(foldersTree, gLnk("S","Station", "MstStation.jsp"))
insDoc(foldersTree, gLnk("S","Supplier", "MstSupplier.jsp"))
insDoc(foldersTree, gLnk("S","Section", "MstSection.jsp"))

insDoc(foldersTree, gLnk("S","Master Codes", "MasterCodeMaintenance.jsp"))	
insDoc(foldersTree, gLnk("S","<nobr>Edit Parameter</nobr>", "MstEditParameter.jsp"))	
aux1 = insFld(foldersTree, gFld("<b><nobr>Report</nobr></b>", ""))
	insDoc(aux1, gLnk("S","<nobr>Track Network</nobr>", "MastersReportParameter.jsp"))
	
insDoc(foldersTree, gLnk("S","User Profile", "UpdateProfile.jsp"))	
//insDoc(foldersTree, gLnk("S","LogOff", "LogoffController"))	