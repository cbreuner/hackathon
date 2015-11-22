<style>
	.mobile-social-share {
    display: block !important;
    min-height: 70px !important;
    float:right;
    margin-top:85px;
}

.mobile-social-share h3 {
    color: inherit;
    float: left;
    font-size: 15px;
    line-height: 20px;
    margin: 25px 25px 0 25px;
}

.share-group {
    float: right;
    margin: 18px 25px 0 0;
}

.btn-group {
    display: inline-block;
    font-size: 0;
    position: relative;
    vertical-align: middle;
    white-space: nowrap;
}

.mobile-social-share ul {
    float: right;
    list-style: none outside none;
    margin: 0;
    min-width: 61px;
    padding: 0;
}

.share {
    min-width: 17px;
}

.mobile-social-share li {
    display: block;
    font-size: 18px;
    list-style: none outside none;
    margin-bottom: 3px;
    margin-left: 4px;
    margin-top: 3px;
}

.btn-share {
    background-color: #BEBEBE;
    border-color: #CCCCCC;
    color: #333333;
}

.btn-twitter {
    background-color: #3399CC !important;
    width: 51px;
    color:#FFFFFF!important;
}

.btn-facebook {
    background-color: #3D5B96 !important;
    width: 51px;
    color:#FFFFFF!important;
}

.btn-facebook {
    background-color: #3D5B96 !important;
    width: 51px;
    color:#FFFFFF!important;
}

.btn-google {
    background-color: #DD3F34 !important;
    width: 51px;
    color:#FFFFFF!important;
}

.btn-linkedin {
    background-color: #1884BB !important;
    width: 51px;
    color:#FFFFFF!important;
}

.btn-pinterest {
    background-color: #CC1E2D !important;
    width: 51px;
    color:#FFFFFF!important;
}

.btn-mail {
    background-color: #FFC90E !important;
    width: 51px;
    color:#FFFFFF!important;
}

.caret {
    border-left: 4px solid rgba(0, 0, 0, 0);
    border-right: 4px solid rgba(0, 0, 0, 0);
    border-top: 4px solid;
    display: inline-block;
    height: 0;
    margin-left: 2px;
    vertical-align: middle;
    width: 0;
}

#socialShare {
    max-width:59px;
    margin-bottom:18px;
}

#socialShare > a{
    padding: 6px 10px 6px 10px;
}

@media (max-width : 320px) {
    #socialHolder{
        padding-left:5px;
        padding-right:5px;
    }
    
    .mobile-social-share h3 {
        margin-left: 0;
        margin-right: 0;
    }
    
    #socialShare{
        margin-left:5px;
        margin-right:5px;
    }
    
    .mobile-social-share h3 {
        font-size: 15px;
    }
}

@media (max-width : 238px) {
    .mobile-social-share h3 {
        font-size: 12px;
    }
}

</style>
		<div class="row mobile-social-share">
 			<div id="socialHolder">
        		<div id="socialShare" class="btn-group share-group">
                    <a data-toggle="dropdown" class="btn btn-info">
                         <i class="fa fa-share-alt fa-inverse"></i>
                    </a>
    				<button href="#" data-toggle="dropdown" class="btn btn-info dropdown-toggle share">
    					<span class="caret"></span>
    				</button>
    				<ul class="dropdown-menu" style="">
        				<li>
    					    <a data-original-title="Twitter" rel="tooltip"  href="#" class="btn btn-twitter" data-placement="left">
								<i class="fa fa-twitter"></i>
							</a>
    					</li>
    					<li>
    						<a data-original-title="Facebook" rel="tooltip"  href="#" class="btn btn-facebook" data-placement="left">
								<i class="fa fa-facebook"></i>
							</a>
    					</li>					
    					<li>
    						<a data-original-title="Google+" rel="tooltip"  href="#" class="btn btn-google" data-placement="left">
								<i class="fa fa-google-plus"></i>
							</a>
    					</li>
    				    <li>
    						<a data-original-title="LinkedIn" rel="tooltip"  href="#" class="btn btn-linkedin" data-placement="left">
								<i class="fa fa-linkedin"></i>
							</a>
    					</li>
    					<li>
    						<a data-original-title="Pinterest" rel="tooltip"  class="btn btn-pinterest" data-placement="left">
								<i class="fa fa-pinterest"></i>
							</a>
    					</li>
                        <li>
    						<a  data-original-title="Email" rel="tooltip" class="btn btn-mail" data-placement="left">
								<i class="fa fa-envelope"></i>
							</a>
    					</li>
                    </ul>
    			</div>
            </div>
        </div>