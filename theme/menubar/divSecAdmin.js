USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree
HIGHLIGHT = 0
foldersTree = gFld("", "")
insDoc(foldersTree, gLnk("S","Role", "MstRole.jsp"))
insDoc(foldersTree, gLnk("S","<nobr>Create User</nobr>", "MstLogin.jsp"))
insDoc(foldersTree, gLnk("S","User Profile", "UpdateProfile.jsp"))
insDoc(foldersTree, gLnk("S","LogOff", "LogoffController"))		