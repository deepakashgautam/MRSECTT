stm_bm(["menu1fb1",430,"","images_old/blank.gif",0,"","",0,1,250,0,50,1,0,0,"","",0],this);
stm_bp("p0",[0,4,0,0,0,2,0,0,100,"",-2,"",-2,90,0,0,"#000000","transparent","",3,0,0,"#FFFFFF"]);

//ASSET MENU
stm_ai("p0i0",[0,"Proposals","","",-1,-1,0,"#","_self","","Work Flow","","",0,0,0,"","",0,0,0,1,1,"",1,"",1,"images_old/top-menu-bg.gif","images_old/top-menu-bg-ovr.gif",3,3,0,0,"#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF","bold 10pt Tahoma","bold 10pt Tahoma",0,0],100,26);
stm_bpx("p1","p0",[1,4,0,0,0,2,0,7,100,"progid:DXImageTransform.Microsoft.Wipe(GradientSize=1.0,wipeStyle=1,motion=forward,enabled=0,Duration=0.30)",5,"progid:DXImageTransform.Microsoft.Wipe(GradientSize=1.0,wipeStyle=0,motion=reverse,enabled=0,Duration=0.30)",7,80]);
//stm_aix("p1i0","p0i0",[0,"     New Proposal","","",-1,-1,0,"NewProposal.jsp","_self","","","","",0,0,0,"","",0,0,0,0,1,"",1,"",1,"images_old/submenu-bg.gif","images_old/submenu-bg-ovr.gif",3,3,0,0,"#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF","8pt Tahoma","8pt Tahoma"],202,24);
//stm_aix("p1i1","p1i0",[0,"     Edit Proposal","","",-1,-1,0,"","_self","",""],202,24);
//stm_aix("p1i2","p1i0",[0,"     Proposal Processing","","",-1,-1,0,"Proposal_Process.jsp","_self","",""],202,24);
stm_aix("p1i0","p0i0",[0,"     Summary","","",-1,-1,0,"SummarySheet.jsp","_self","","","","",0,0,0,"","",0,0,0,0,1,"",1,"",1,"images_old/submenu-bg.gif","images_old/submenu-bg-ovr.gif",3,3,0,0,"#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF","8pt Tahoma","8pt Tahoma"],202,24);
//stm_aix("p1i1","p1i0",[0,"     AM's Committee Remarks","","",-1,-1,0,"AMComitteeList.jsp","_self","",""],202,24);
stm_aix("p1i2","p1i0",[0,"     Nodal Directorate Recommendation","","",-1,-1,0,"NodalRecommend.jsp","_self","",""],202,24);
stm_aix("p1i2","p1i0",[0,"     AM's Committee Recommendation","","",-1,-1,0,"AMRecommend.jsp","_self","",""],202,24);
stm_aix("p1i3","p1i0",[0,"     Receipient List","","",-1,-1,0,"ReceipentList.jsp","_self","",""],202,24);
stm_aix("p1i4","p1i0",[0,"     Users","","",-1,-1,0,"CreateLogin.jsp","_self","",""],202,24);
stm_ep();
stm_ai("p0i1",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

//INSPECTION MENU
stm_aix("p0i3","p0i0",[0,"Works","","",-1,-1,0,"#","_self","",""],130,26);
stm_bpx("p3","p1",[1,4]);
//stm_aix("p3i1","p1i0",[0,"     Ceiling","","",-1,-1,0,"Ceiling.jsp","_self","",""],202,24);
stm_aix("p3i1","p1i0",[0,"     Modification","","",-1,-1,0,"WorkModify.jsp","_self","",""],202,24);
stm_aix("p3i2","p1i0",[0,"     Lumpsum","","",-1,-1,0,"Lumpsum.jsp","_self","",""],202,24);
stm_ep(); 
stm_ai("p0i3",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

//INSPECTION MENU
stm_aix("p0i4","p0i0",[0,"Ceiling","","",-1,-1,0,"Ceiling.jsp","_self","",""],130,26);
stm_bpx("p4","p3",[1,4]);
//stm_aix("p3i1","p1i0",[0,"     Ceiling","","",-1,-1,0,"Ceiling.jsp","_self","",""],202,24);
//stm_aix("p3i1","p1i0",[0,"     Modification","","",-1,-1,0,"WorkModify.jsp","_self","",""],202,24);
//stm_aix("p3i1","p1i0",[0,"     Data Entry","","",-1,-1,0,"DataEntry.jsp","_self","",""],202,24);
stm_ep(); 
stm_ai("p0i4",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

//REPORT MENU
stm_aix("p0i7","p0i0",[0,"Report","","",-1,-1,0,"#","_self","","Report"],100,26);
stm_bpx("p7","p3",[]);
stm_aix("p7i1","p1i0",[0,"     PWP/FWP Report (VOL I, II &amp; III)","","",-1,-1,0,"PWPReport.jsp","_self","",""],202,24);
//stm_aix("p7i2","p1i0",[0,"     PWP/FWP Report (VOL IV)","","",-1,-1,0,"PWPReportVolIVMain.jsp","_self","",""],202,24);
stm_aix("p7i3","p1i0",[0,"     PWP/FWP Report Selected Nos.","","",-1,-1,0,"PWPReportSelectedNos.jsp","_self","",""],202,24);
stm_aix("p7i4","p1i0",[0,"     Throwforward Ratio","","",-1,-1,0,"RepThrowRatio.jsp","_self","",""],202,24);
stm_ep();
stm_ai("p0i7",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

stm_em();
