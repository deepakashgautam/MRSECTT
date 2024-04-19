stm_bm(["menu4fb1",430,"","images_old/blank.gif",0,"","",0,1,250,0,50,1,0,0,"","",0],this);
stm_bp("p0",[0,4,0,0,0,2,0,0,100,"",-2,"",-2,90,0,0,"#000000","transparent","",3,0,0,"#FFFFFF"]);

//ASSET MENU
stm_ai("p0i0",[0,"    References    ","","",-1,-1,0,"#","_self","","","","",0,0,0,"","",0,0,0,1,1,"",1,"",1,"images_old/top-menu-bg.gif","images_old/top-menu-bg-ovr.gif",3,3,0,0,"#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF","bold 10pt Tahoma","bold 10pt Tahoma",0,0],100,26);
stm_bpx("p1","p0",[1,4,0,0,0,2,0,7,100,"progid:DXImageTransform.Microsoft.Wipe(GradientSize=1.0,wipeStyle=1,motion=forward,enabled=0,Duration=0.30)",5,"progid:DXImageTransform.Microsoft.Wipe(GradientSize=1.0,wipeStyle=0,motion=reverse,enabled=0,Duration=0.30)",7,80]);
//stm_aix("p1i0","p0i0",[0,"     New Proposal","","",-1,-1,0,"NewProposal.jsp","_self","","","","",0,0,0,"","",0,0,0,0,1,"",1,"",1,"images_old/submenu-bg.gif","images_old/submenu-bg-ovr.gif",3,3,0,0,"#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF","8pt Tahoma","8pt Tahoma"],202,24);
//stm_aix("p1i1","p1i0",[0,"     Edit Proposal","","",-1,-1,0,"","_self","",""],202,24);
//stm_aix("p1i2","p1i0",[0,"     Proposal Processing","","",-1,-1,0,"Proposal_Process.jsp","_self","",""],202,24);
stm_aix("p1i0","p0i0",[0,"     Letters","","",-1,-1,0,"ProposalBankShortlisted.jsp","_self","","","","",0,0,0,"","",0,0,0,0,1,"",1,"",1,"images_old/submenu-bg.gif","images_old/submenu-bg-ovr.gif",3,3,0,0,"#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF","8pt Tahoma","8pt Tahoma"],202,24);

stm_ep();
stm_ai("p0i1",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

/*//INSPECTION MENU
stm_aix("p0i3","p0i0",[0,"   Sanctioned Works    ","","",-1,-1,0,"#","_self","",""],130,26);
stm_bpx("p3","p1",[1,4]);
stm_aix("p3i1","p1i0",[0,"     Allotment of Executing Agency","","",-1,-1,0,"ExecAgencyDelegation.jsp","_self","",""],202,24);
stm_aix("p3i2","p1i0",[0,"     Status of Work","","",-1,-1,0,"WorkStatusMain.jsp","_self","",""],202,24);
stm_ep(); 
stm_ai("p0i3",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

//REPORT MENU
stm_aix("p0i7","p0i0",[0,"  Report  ","","",-1,-1,0,"#","_self","",""],100,26);
stm_bpx("p7","p1",[]);
stm_aix("p7i0","p1i0",[0," WP Volumes","","",-1,-1,0,"#","_self","","","","",0,0,0,"images_old/arrow_r.gif","images_old/arrow_r.gif",14,7],180,20);
stm_bpx("p7","p1",[1,2,0,0,0,2,0,0]);
stm_aix("p7i1","p1i0",[0,"     WP Report (VOL I, II &amp; III)","","",-1,-1,0,"PWPReport.jsp","_self","",""],202,24);
stm_aix("p7i2","p1i0",[0,"     WP Report (VOL IV)","","",-1,-1,0,"PWPReportVolIVMain.jsp","_self","",""],202,24);
stm_aix("p7i3","p1i0",[0,"     WP Report Selected Nos.","","",-1,-1,0,"PWPReportSelectedNos.jsp","_self","",""],202,24);
stm_aix("p7i4","p1i0",[0,"     WP Index Report","","",-1,-1,0,"PWPIndex.jsp","_self","",""],202,24);
stm_aix("p7i4","p1i0",[0,"     WP Justification Report","","",-1,-1,0,"PWPJustification.jsp","_self","",""],202,24);
stm_ep();
stm_aix("p7i4","p1i0",[0,"     LAW Book Report","","",-1,-1,0,"LAWBook.jsp","_self","",""],202,24);
stm_aix("p7i5","p1i0",[0,"     Throwforward Ratio","","",-1,-1,0,"RepThrowRatio.jsp","_self","",""],202,24);
stm_aix("p7i6","p1i0",[0,"     Ceiling","","",-1,-1,0,"Ceiling.jsp","_self","",""],202,24);
stm_aix("p7i6","p1i0",[0,"     Exception Report","","",-1,-1,0,"ExceptionReport.jsp","_self","",""],202,24);
stm_aix("p7i7","p1i0",[0,"     AM Committee Minutes","","",-1,-1,0,"MoMPrint.jsp","_self","",""],202,24);
stm_aix("p7i8","p1i0",[0,"     Exception Report: Summary","","",-1,-1,0,"ExceptionSummary.jsp","_self","",""],202,24);
stm_aix("p7i9","p1i0",[0,"     DRM/CWM Limits","","",-1,-1,0,"WIPLimits.jsp","_self","",""],202,24);
stm_aix("p7i10","p1i0",[0,"     List of All Works","","",-1,-1,0,"DirectoratesRly.jsp","_self","",""],202,24);
stm_aix("p7i9","p1i0",[0,"     WIP : Summary","","",-1,-1,0,"WorkCostSummary.jsp","_self","",""],202,24);
stm_aix("p7i10","p1i0",[0,"     Customized Reports","","",-1,-1,0,"CustomReport.jsp","_self","",""],202,24);
//stm_aix("p7i10","p1i0",[0,"     New Sanctioned Works","","",-1,-1,0,"WorkModify.jsp?catgcode=NW","_self","",""],202,24);
stm_ep();
stm_ai("p0i7",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

stm_aix("p0i7","p0i0",[0,"  Administration  ","","",-1,-1,0,"#","_self","",""],100,26);
stm_bpx("p7","p3",[]);
//stm_aix("p7i1","p1i0",[0,"     Change Password ","","",-1,-1,0,"ChangePassword.jsp","_self","",""],202,24);
stm_aix("p7i1","p1i0",[0,"     Executing Agency","","",-1,-1,0,"ExecutingAgencyRly.jsp","_self","",""],202,24);
stm_ep();
stm_ai("p0i7",[6,1,"transparent","images_old/separator.gif",1,26,0]);
stm_ep();

stm_em();
*/