package org.pinae.sarabi.service;

public final class Http {

	/* HTTP Method */

	public static final String HTTP_GET = "GET";

	public static final String HTTP_POST = "POST";

	public static final String HTTP_HEAD = "HEAD";

	public static final String HTTP_DELETE = "DELETE";

	public static final String HTTP_PUT = "PUT";

	public static final String HTTP_OPTIONS = "OPTIONS";

	/*
	 * HTTP Response Code
	 * 
	 * 1xx Informational <br/> 2xx Success <br/> 3xx Redirection <br/> 4xx
	 * Client Error <br/> 5xx Server Error <br/>
	 * 
	 */

	public static final int HTTP_NONE = 0;
	
	/* 100 Continue */
	public static final int HTTP_CONTINUE = 100;

	/* 101 Switching Protocols */
	public static final int HTTP_SWITCHING_PROTOCOLS = 101;

	/* 102 Processing (WebDAV) */
	public static final int HTTP_PROCESSING = 102;

	/* 200 OK */
	public static final int HTTP_OK = 200;

	/* 201 Created */
	public static final int HTTP_CREATE = 201;

	/* 202 Accepted */
	public static final int HTTP_ACCEPTED = 202;

	/* 203 Non-Authoritative Information */
	public static final int HTTP_NON_AUTH_INFO = 203;

	/* 204 No Content */
	public static final int HTTP_NO_CONTENT = 204;

	/* 205 Reset Content */
	public static final int HTTP_RESET_CONTENT = 205;

	/* 206 Partial Content */
	public static final int HTTP_PARTIAL_CONTENT = 206;

	/* 207 Multi-Status (WebDAV) */
	public static final int HTTP_MULTI_STATUS = 207;

	/* 208 Already Reported (WebDAV) */
	public static final int HTTP_ALREADY_REPORTED = 208;

	/* 226 IM Used */
	public static final int HTTP_IM_USED = 226;

	/* 300 Multiple Choices */
	public static final int HTTP_MULTIPLE_CHOICES = 300;

	/* 301 Moved Permanently */
	public static final int HTTP_MOVED_PERMANENTLY = 301;

	/* 302 Found */
	public static final int HTTP_FOUND = 302;

	/* 303 See Other */
	public static final int HTTP_SEE_OTHER = 303;

	/* 304 Not Modified */
	public static final int HTTP_NOT_MODIFIED = 304;

	/* 305 Use Proxy */
	public static final int HTTP_USE_PROXY = 305;

	/* 307 Temporary Redirect */
	public static final int HTTP_TEMP_REDIRECT = 307;

	/* 308 Permanent Redirect (experiemental) */
	public static final int HTTP_PERMANENT_REDIRECT = 308;

	/* 400 Bad Request */
	public static final int HTTP_BAD_REQUEST = 400;

	/* 401 Unauthorized */
	public static final int HTTP_UNAUTHORIZED = 401;

	/* 402 Payment Required */
	public static final int HTTP_PAYMENT_REQUIRED = 402;

	/* 403 Forbidden */
	public static final int HTTP_FORBIDDEN = 403;

	/* 404 Not Found */
	public static final int HTTP_NOT_FOUND = 404;

	/* 405 Method Not Allowed */
	public static final int HTTP_METHOD_NOT_ALLOWED = 405;

	/* 406 Not Acceptable */
	public static final int HTTP_NOT_ACCEPTABLE = 406;

	/* 407 Proxy Authentication Required */
	public static final int HTTP_PROXY_AUTH_REQUIRED = 407;

	/* 408 Request Timeout */
	public static final int HTTP_REQUEST_TIMEOUT = 408;

	/* 409 Conflict */
	public static final int HTTP_CONFLICT = 409;

	/* 410 Gone */
	public static final int HTTP_GONE = 410;

	/* 411 Length Required */
	public static final int HTTP_LENGTH_REQUIRED = 411;

	/* 412 Precondition Failed */
	public static final int HTTP_PRECONDITION_FAILED = 412;

	/* 413 Request Entity Too Large */
	public static final int HTTP_REQUEST_ENTITY_TOO_LARGE = 413;

	/* 414 Request-URI Too Long */
	public static final int HTTP_REQUEST_URI_TOO_LONG = 414;

	/* 415 Unsupported Media Type */
	public static final int HTTP_UNSUPPORTED_MEDIA_TYPE = 415;

	/* 416 Requested Range Not Satisfiable */
	public static final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

	/* 417 Expectation Failed */
	public static final int HTTP_EXPECTATION_FAILED = 417;

	/* 418 I'm a teapot (RFC 2324) */
	public static final int HTTP_IM_TEAPOT = 418;

	/* 420 Enhance Your Calm (Twitter) */
	public static final int HTTP_ENHANCE_YOUR_CALM = 420;

	/* 422 Unprocessable Entity (WebDAV) */
	public static final int HTTP_UNPROCESSABLE_ENTITY = 422;

	/* 423 Locked (WebDAV) */
	public static final int HTTP_LOCKED = 423;

	/* 424 Failed Dependency (WebDAV) */
	public static final int HTTP_FAILED_DEPENDENCY = 424;

	/* 426 Upgrade Required */
	public static final int HTTP_UPGRADE_REQUIRED = 426;

	/* 428 Precondition Required */
	public static final int HTTP_PRECONDITION_REQUIRED = 428;

	/* 429 Too Many Requests */
	public static final int HTTP_TOO_MANY_REQUESTS = 429;

	/* 431 Request Header Fields Too Large */
	public static final int HTTP_REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

	/* 444 No Response (Nginx) */
	public static final int HTTP_NO_RESPONSE = 444;

	/* 449 Retry With (Microsoft) */
	public static final int HTTP_RETRY_WITH = 449;

	/* 450 Blocked by Windows Parental Controls (Microsoft) */
	public static final int HTTP_BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS = 450;

	/* 451 Unavailable For Legal Reasons */
	public static final int HTTP_UNAVAILABLE_FOR_LEGAL_REASONS = 451;

	/* 499 Client Closed Request (Nginx) */
	public static final int HTTP_CLIENT_CLOSED_REQUEST = 499;

	/* 500 Internal Server Error */
	public static final int HTTP_INTERNAL_SERVER_ERROR = 500;

	/* 501 Not Implemented */
	public static final int HTTP_NOT_IMPLEMENTED = 501;

	/* 502 Bad Gateway */
	public static final int HTTP_BAD_GATEWAY = 502;

	/* 503 Service Unavailable */
	public static final int HTTP_SERVICE_UNAVAILABLE = 503;

	/* 504 Gateway Timeout */
	public static final int HTTP_GATEWAY_TIMEOUT = 504;

	/* 505 HTTP Version Not Supported */
	public static final int HTTP_HTTP_VERSION_NOT_SUPPORTED = 505;

	/* 506 Variant Also Negotiates (Experimental) */
	public static final int HTTP_VARIANT_ALSO_NEGOTIATES = 506;

	/* 507 Insufficient Storage (WebDAV) */
	public static final int HTTP_INSUFFICIENT_STORAGE = 507;

	/* 508 Loop Detected (WebDAV) */
	public static final int HTTP_LOOP_DETECTED = 508;

	/* 509 Bandwidth Limit Exceeded (Apache) */
	public static final int HTTP_BANDWIDTH_LIMIT_EXCEEDED = 509;

	/* 510 Not Extended */
	public static final int HTTP_NOT_EXTENDED = 510;

	/* 511 Network Authentication Required */
	public static final int HTTP_NETWORK_AUTH_REQUIRED = 511;

	/* 598 Network read timeout error */
	public static final int HTTP_NETWORK_READ_TIMEOUT_ERROR = 598;

	/* 599 Network connect timeout error */
	public static final int HTTP_NETWORK_CONNECT_TIMEOUT_ERROR = 599;

	/* Http Content-Type */
	public static final String APPLICATION_PDF = "application/pdf";

	public static final String APPLICATION_POSTSCRIPT = "application/postscript";

	public static final String APPLICATION_ATOM_XML = "application/atom+xml";

	public static final String APPLICATION_ECMASCRIPT = "application/ecmascript";

	public static final String APPLICATION_EDI_X12 = "application/EDI-X12";

	public static final String APPLICATION_EDIFACT = "application/EDIFACT";

	public static final String APPLICATION_JSON = "application/json";

	public static final String APPLICATION_JAVASCRIPT = "application/javascript";

	public static final String APPLICATION_OGG = "application/ogg";

	public static final String APPLICATION_RDF_XML = "application/rdf+xml";

	public static final String APPLICATION_RSS_XML = "application/rss+xml";

	public static final String APPLICATION_SOAP_XML = "application/soap+xml";

	public static final String APPLICATION_FONT_WOFF = "application/font-woff";

	public static final String APPLICATION_XHTML_XML = "application/xhtml+xml";

	public static final String APPLICATION_XML = "application/xml";

	public static final String APPLICATION_XML_DTD = "application/xml-dtd";

	public static final String APPLICATION_XOP_XML = "application/xop+xml";

	public static final String APPLICATION_ZIP = "application/zip";

	public static final String APPLICATION_GZIP = "application/gzip";

	public static final String APPLICATION_X_XLS = "application/x-xls";

	public static final String APPLICATION_X_001 = "application/x-001";

	public static final String APPLICATION_X_301 = "application/x-301";

	public static final String APPLICATION_X_906 = "application/x-906";

	public static final String APPLICATION_X_A11 = "application/x-a11";

	public static final String APPLICATION_VND_ADOBE_WORKFLOW = "application/vnd.adobe.workflow";

	public static final String APPLICATION_X_BMP = "application/x-bmp";

	public static final String APPLICATION_X_C4T = "application/x-c4t";

	public static final String APPLICATION_X_CALS = "application/x-cals";

	public static final String APPLICATION_X_NETCDF = "application/x-netcdf";

	public static final String APPLICATION_X_CEL = "application/x-cel";

	public static final String APPLICATION_X_G4 = "application/x-g4";

	public static final String APPLICATION_X_CIT = "application/x-cit";

	public static final String APPLICATION_X_BOT = "application/x-bot";

	public static final String APPLICATION_X_C90 = "application/x-c90";

	public static final String APPLICATION_VND_MS_PKI_SECCAT = "application/vnd.ms-pki.seccat";

	public static final String APPLICATION_X_CDR = "application/x-cdr";

	public static final String APPLICATION_X_X509_CA_CERT = "application/x-x509-ca-cert";

	public static final String APPLICATION_X_CGM = "application/x-cgm";

	public static final String APPLICATION_X_CMX = "application/x-cmx";

	public static final String APPLICATION_PKIX_CRL = "application/pkix-crl";

	public static final String APPLICATION_X_CSI = "application/x-csi";

	public static final String APPLICATION_X_CUT = "application/x-cut";

	public static final String APPLICATION_X_DBM = "application/x-dbm";

	public static final String APPLICATION_X_CMP = "application/x-cmp";

	public static final String APPLICATION_X_COT = "application/x-cot";

	public static final String APPLICATION_X_DBF = "application/x-dbf";

	public static final String APPLICATION_X_DBX = "application/x-dbx";

	public static final String APPLICATION_X_DCX = "application/x-dcx";

	public static final String APPLICATION_X_DGN = "application/x-dgn";

	public static final String APPLICATION_X_MSDOWNLOAD = "application/x-msdownload";

	public static final String APPLICATION_MSWORD = "application/msword";

	public static final String APPLICATION_X_DIB = "application/x-dib";

	public static final String APPLICATION_X_DRW = "application/x-drw";

	public static final String APPLICATION_X_DWF = "application/x-dwf";

	public static final String APPLICATION_X_DXB = "application/x-dxb";

	public static final String APPLICATION_VND_ADOBE_EDN = "application/vnd.adobe.edn";

	public static final String APPLICATION_X_DWG = "application/x-dwg";

	public static final String APPLICATION_X_DXF = "application/x-dxf";

	public static final String APPLICATION_X_EMF = "application/x-emf";

	public static final String APPLICATION_X_EPI = "application/x-epi";

	public static final String APPLICATION_VND_FDF = "application/vnd.fdf";

	public static final String APPLICATION_X_PS = "application/x-ps";

	public static final String APPLICATION_X_EBX = "application/x-ebx";

	public static final String APPLICATION_FRACTALS = "application/fractals";

	public static final String APPLICATION_X_FRM = "application/x-frm";

	public static final String APPLICATION_X_GBR = "application/x-gbr";

	public static final String APPLICATION_X_GL2 = "application/x-gl2";

	public static final String APPLICATION_X_HGL = "application/x-hgl";

	public static final String APPLICATION_X_HPGL = "application/x-hpgl";

	public static final String APPLICATION_MAC_BINHEX40 = "application/mac-binhex40";

	public static final String APPLICATION_HTA = "application/hta";

	public static final String APPLICATION_X_GP4 = "application/x-gp4";

	public static final String APPLICATION_X_HMR = "application/x-hmr";

	public static final String APPLICATION_X_HPL = "application/x-hpl";

	public static final String APPLICATION_X_HRF = "application/x-hrf";

	public static final String APPLICATION_X_ICB = "application/x-icb";

	public static final String APPLICATION_X_ICO = "application/x-ico";

	public static final String APPLICATION_X_IPHONE = "application/x-iphone";

	public static final String APPLICATION_X_INTERNET_SIGNUP = "application/x-internet-signup";

	public static final String APPLICATION_X_IFF = "application/x-iff";

	public static final String APPLICATION_X_IGS = "application/x-igs";

	public static final String APPLICATION_X_IMG = "application/x-img";

	public static final String APPLICATION_X_JPE = "application/x-jpe";

	public static final String APPLICATION_X_JAVASCRIPT = "application/x-javascript";

	public static final String APPLICATION_X_JPG = "application/x-jpg";

	public static final String APPLICATION_X_LAPLAYER_REG = "application/x-laplayer-reg";

	public static final String APPLICATION_X_LATEX = "application/x-latex";

	public static final String APPLICATION_X_LBM = "application/x-lbm";

	public static final String APPLICATION_X_LTR = "application/x-ltr";

	public static final String APPLICATION_X_TROFF_MAN = "application/x-troff-man";

	public static final String APPLICATION_MSACCESS = "application/msaccess";

	public static final String APPLICATION_X_MAC = "application/x-mac";

	public static final String APPLICATION_X_MDB = "application/x-mdb";

	public static final String APPLICATION_X_SHOCKWAVE_FLASH = "application/x-shockwave-flash";

	public static final String APPLICATION_X_MI = "application/x-mi";

	public static final String APPLICATION_X_MIL = "application/x-mil";

	public static final String APPLICATION_VND_MS_PROJECT = "application/vnd.ms-project";

	public static final String APPLICATION_X_MMXP = "application/x-mmxp";

	public static final String APPLICATION_X_NRF = "application/x-nrf";

	public static final String APPLICATION_X_OUT = "application/x-out";

	public static final String APPLICATION_X_PKCS12 = "application/x-pkcs12";

	public static final String APPLICATION_PKCS7_MIME = "application/pkcs7-mime";

	public static final String APPLICATION_X_PKCS7_CERTREQRESP = "application/x-pkcs7-certreqresp";

	public static final String APPLICATION_X_PC5 = "application/x-pc5";

	public static final String APPLICATION_X_PCL = "application/x-pcl";

	public static final String APPLICATION_VND_ADOBE_PDX = "application/vnd.adobe.pdx";

	public static final String APPLICATION_X_PGL = "application/x-pgl";

	public static final String APPLICATION_VND_MS_PKI_PKO = "application/vnd.ms-pki.pko";

	public static final String APPLICATION_PKCS10 = "application/pkcs10";

	public static final String APPLICATION_X_PKCS7_CERTIFICATES = "application/x-pkcs7-certificates";

	public static final String APPLICATION_PKCS7_SIGNATURE = "application/pkcs7-signature";

	public static final String APPLICATION_X_PCI = "application/x-pci";

	public static final String APPLICATION_X_PCX = "application/x-pcx";

	public static final String APPLICATION_X_PIC = "application/x-pic";

	public static final String APPLICATION_X_PERL = "application/x-perl";

	public static final String APPLICATION_X_PLT = "application/x-plt";

	public static final String APPLICATION_X_PNG = "application/x-png";

	public static final String APPLICATION_VND_MS_POWERPOINT = "application/vnd.ms-powerpoint";

	public static final String APPLICATION_X_PPT = "application/x-ppt";

	public static final String APPLICATION_PICS_RULES = "application/pics-rules";

	public static final String APPLICATION_X_PRT = "application/x-prt";

	public static final String AUDIO_VND_RN_REALAUDIO = "audio/vnd.rn-realaudio";

	public static final String APPLICATION_X_RAS = "application/x-ras";

	public static final String APPLICATION_X_PPM = "application/x-ppm";

	public static final String APPLICATION_X_PR = "application/x-pr";

	public static final String APPLICATION_X_PRN = "application/x-prn";

	public static final String APPLICATION_X_PTN = "application/x-ptn";

	public static final String APPLICATION_X_RED = "application/x-red";

	public static final String APPLICATION_VND_RN_REALSYSTEM_RJS = "application/vnd.rn-realsystem-rjs";

	public static final String APPLICATION_X_RLC = "application/x-rlc";

	public static final String APPLICATION_VND_RN_REALMEDIA = "application/vnd.rn-realmedia";

	public static final String APPLICATION_RAT_FILE = "application/rat-file";

	public static final String APPLICATION_VND_RN_RECORDING = "application/vnd.rn-recording";

	public static final String APPLICATION_X_RGB = "application/x-rgb";

	public static final String APPLICATION_VND_RN_REALSYSTEM_RJT = "application/vnd.rn-realsystem-rjt";

	public static final String APPLICATION_X_RLE = "application/x-rle";

	public static final String APPLICATION_VND_ADOBE_RMF = "application/vnd.adobe.rmf";

	public static final String APPLICATION_VND_RN_REALSYSTEM_RMJ = "application/vnd.rn-realsystem-rmj";

	public static final String APPLICATION_VND_RN_RN_MUSIC_PACKAGE = "application/vnd.rn-rn_music_package";

	public static final String APPLICATION_VND_RN_REALMEDIA_VBR = "application/vnd.rn-realmedia-vbr";

	public static final String APPLICATION_VND_RN_REALPLAYER = "application/vnd.rn-realplayer";

	public static final String APPLICATION_VND_RN_REALMEDIA_SECURE = "application/vnd.rn-realmedia-secure";

	public static final String APPLICATION_VND_RN_REALSYSTEM_RMX = "application/vnd.rn-realsystem-rmx";

	public static final String APPLICATION_VND_RN_RSML = "application/vnd.rn-rsml";

	public static final String APPLICATION_X_SAT = "application/x-sat";

	public static final String APPLICATION_X_SDW = "application/x-sdw";

	public static final String APPLICATION_X_SLB = "application/x-slb";

	public static final String APPLICATION_X_RTF = "application/x-rtf";

	public static final String APPLICATION_X_SAM = "application/x-sam";

	public static final String APPLICATION_SDP = "application/sdp";

	public static final String APPLICATION_X_STUFFIT = "application/x-stuffit";

	public static final String APPLICATION_X_SLD = "application/x-sld";

	public static final String APPLICATION_SMIL = "application/smil";

	public static final String APPLICATION_X_SMK = "application/x-smk";

	public static final String APPLICATION_FUTURESPLASH = "application/futuresplash";

	public static final String APPLICATION_STREAMINGMEDIA = "application/streamingmedia";

	public static final String APPLICATION_VND_MS_PKI_STL = "application/vnd.ms-pki.stl";

	public static final String APPLICATION_VND_MS_PKI_CERTSTORE = "application/vnd.ms-pki.certstore";

	public static final String APPLICATION_X_TDF = "application/x-tdf";

	public static final String APPLICATION_X_TGA = "application/x-tga";

	public static final String APPLICATION_X_STY = "application/x-sty";

	public static final String APPLICATION_X_TG4 = "application/x-tg4";

	public static final String APPLICATION_X_TIF = "application/x-tif";

	public static final String APPLICATION_VND_VISIO = "application/vnd.visio";

	public static final String APPLICATION_X_VPEG005 = "application/x-vpeg005";

	public static final String APPLICATION_X_VSD = "application/x-vsd";

	public static final String APPLICATION_X_BITTORRENT = "application/x-bittorrent";

	public static final String APPLICATION_X_VDA = "application/x-vda";

	public static final String APPLICATION_X_VST = "application/x-vst";

	public static final String APPLICATION_X_WB1 = "application/x-wb1";

	public static final String APPLICATION_X_WB3 = "application/x-wb3";

	public static final String APPLICATION_X_WK4 = "application/x-wk4";

	public static final String APPLICATION_X_WKS = "application/x-wks";

	public static final String APPLICATION_X_WB2 = "application/x-wb2";

	public static final String APPLICATION_X_WK3 = "application/x-wk3";

	public static final String APPLICATION_X_WKQ = "application/x-wkq";

	public static final String APPLICATION_X_WMF = "application/x-wmf";

	public static final String APPLICATION_X_MS_WMD = "application/x-ms-wmd";

	public static final String APPLICATION_X_WP6 = "application/x-wp6";

	public static final String APPLICATION_X_WPG = "application/x-wpg";

	public static final String APPLICATION_X_WQ1 = "application/x-wq1";

	public static final String APPLICATION_X_WRI = "application/x-wri";

	public static final String APPLICATION_X_WS = "application/x-ws";

	public static final String APPLICATION_X_MS_WMZ = "application/x-ms-wmz";

	public static final String APPLICATION_X_WPD = "application/x-wpd";

	public static final String APPLICATION_VND_MS_WPL = "application/vnd.ms-wpl";

	public static final String APPLICATION_X_WR1 = "application/x-wr1";

	public static final String APPLICATION_X_WRK = "application/x-wrk";

	public static final String APPLICATION_VND_ADOBE_XDP = "application/vnd.adobe.xdp";

	public static final String APPLICATION_VND_ADOBE_XFD = "application/vnd.adobe.xfd";

	public static final String APPLICATION_VND_ADOBE_XFDF = "application/vnd.adobe.xfdf";

	public static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";

	public static final String APPLICATION_X_XWD = "application/x-xwd";

	public static final String APPLICATION_VND_SYMBIAN_INSTALL = "application/vnd.symbian.install";

	public static final String APPLICATION_X_X_T = "application/x-x_t";

	public static final String APPLICATION_VND_ANDROID_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";

	public static final String APPLICATION_X_X_B = "application/x-x_b";

	public static final String APPLICATION_VND_IPHONE = "application/vnd.iphone";

	public static final String APPLICATION_X_SILVERLIGHT_APP = "application/x-silverlight-app";

	public static final String APPLICATION_X_XLW = "application/x-xlw";

	public static final String AUDIO_SCPLS = "audio/scpls";

	public static final String APPLICATION_X_ANV = "application/x-anv";

	public static final String APPLICATION_X_ICQ = "application/x-icq";

	public static final String TEXT_H323 = "text/h323";

	public static final String TEXT_XML = "text/xml";

	public static final String TEXT_ASA = "text/asa";

	public static final String TEXT_ASP = "text/asp";

	public static final String TEXT_CSS = "text/css";

	public static final String TEXT_CSV = "text/csv";

	public static final String TEXT_X_COMPONENT = "text/x-component";

	public static final String TEXT_HTML = "text/html";

	public static final String TEXT_WEBVIEWHTML = "text/webviewhtml";

	public static final String TEXT_VND_RN_REALTEXT = "text/vnd.rn-realtext";

	public static final String TEXT_PLAIN = "text/plain";

	public static final String TEXT_IULS = "text/iuls";

	public static final String TEXT_X_VCARD = "text/x-vcard";

	public static final String TEXT_VND_WAP_WML = "text/vnd.wap.wml";

	public static final String TEXT_SCRIPTLET = "text/scriptlet";

	public static final String TEXT_X_MS_ODC = "text/x-ms-odc";

	public static final String TEXT_VND_RN_REALTEXT3D = "text/vnd.rn-realtext3d";

	public static final String AUDIO_X_PN_REALAUDIO_PLUGIN = "audio/x-pn-realaudio-plugin";

	public static final String AUDIO_X_MEI_AAC = "audio/x-mei-aac";

	public static final String AUDIO_AIFF = "audio/aiff";

	public static final String AUDIO_BASIC = "audio/basic";

	public static final String AUDIO_X_LIQUID_FILE = "audio/x-liquid-file";

	public static final String AUDIO_X_LIQUID_SECURE = "audio/x-liquid-secure";

	public static final String AUDIO_X_LA_LMS = "audio/x-la-lms";

	public static final String AUDIO_MPEGURL = "audio/mpegurl";

	public static final String AUDIO_MID = "audio/mid";

	public static final String AUDIO_MP2 = "audio/mp2";

	public static final String AUDIO_MP3 = "audio/mp3";

	public static final String AUDIO_MP4 = "audio/mp4";

	public static final String AUDIO_X_MUSICNET_DOWNLOAD = "audio/x-musicnet-download";

	public static final String AUDIO_MP1 = "audio/mp1";

	public static final String AUDIO_X_MUSICNET_STREAM = "audio/x-musicnet-stream";

	public static final String AUDIO_RN_MPEG = "audio/rn-mpeg";

	public static final String AUDIO_X_PN_REALAUDIO = "audio/x-pn-realaudio";

	public static final String AUDIO_WAV = "audio/wav";

	public static final String AUDIO_X_MS_WAX = "audio/x-ms-wax";

	public static final String AUDIO_X_MS_WMA = "audio/x-ms-wma";

	public static final String VIDEO_X_MS_ASF = "video/x-ms-asf";

	public static final String VIDEO_VND_RN_REALVIDEO = "video/vnd.rn-realvideo";

	public static final String VIDEO_AVI = "video/avi";

	public static final String VIDEO_X_IVF = "video/x-ivf";

	public static final String VIDEO_X_MPEG = "video/x-mpeg";

	public static final String VIDEO_MPEG4 = "video/mpeg4";

	public static final String VIDEO_X_SGI_MOVIE = "video/x-sgi-movie";

	public static final String VIDEO_MPEG = "video/mpeg";

	public static final String VIDEO_X_MPG = "video/x-mpg";

	public static final String VIDEO_MPG = "video/mpg";

	public static final String VIDEO_X_MS_WM = "video/x-ms-wm";

	public static final String VIDEO_X_MS_WMV = "video/x-ms-wmv";

	public static final String VIDEO_X_MS_WMX = "video/x-ms-wmx";

	public static final String VIDEO_X_MS_WVX = "video/x-ms-wvx";

	public static final String IMAGE_TIFF = "image/tiff";

	public static final String IMAGE_FAX = "image/fax";

	public static final String IMAGE_GIF = "image/gif";

	public static final String IMAGE_X_ICON = "image/x-icon";

	public static final String IMAGE_JPEG = "image/jpeg";

	public static final String IMAGE_PNETVUE = "image/pnetvue";

	public static final String IMAGE_PNG = "image/png";

	public static final String IMAGE_VND_RN_REALPIX = "image/vnd.rn-realpix";

	public static final String IMAGE_VND_WAP_WBMP = "image/vnd.wap.wbmp";

	public static final String MESSAGE_RFC822 = "message/rfc822";

	public static final String DRAWING_907 = "drawing/907";

	public static final String DRAWING_X_SLK = "drawing/x-slk";

	public static final String DRAWING_X_TOP = "drawing/x-top";

}
