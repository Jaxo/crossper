({
    appDir: './',
    baseUrl: './js',
    dir: './min',
    modules: [
			{ name: "mobile/main" },
			{ name: "web/main" }
    ],
	fileExclusionRegExp: /^(r|build)\.js$/,
	optimizeCss: 'standard',
	skipDirOptimize :true,
	optimize:'uglify',
    paths: {
		'jQuery': 'lib/jquery-1.8.2.min',
		'json2'		: 'lib/backbone/json2',
		'commomjs'	: 'mobile/common-js',
		'underscore': 'lib/backbone/underscore-min',
		'handlebar'	: 'lib/backbone/handlebar',
		'lodash'	: 'lib/backbone/lodash',
		'backbone'	: 'lib/backbone/backbone-min',
		'mobileRoot'	: 'mobile',
		'mobileViews'	: 'views/mobile',
		'models'		: 'models',
		'collections'	: 'collections',
		'crossper-util'	: 'app/crossper-utils',
		'text'			: 'text',
        'jquery.ui.widget' : 'lib/jquery-ui-core1.9.2',
        'jquery-transport' : 'lib/jquery.iframe-transport',
        'jquery-file-upload' : 'lib/jquery.fileupload',
		'templates' : '../templates',
		
		'webRoot'	: 'web',
		'webViews'	: 'views/web',
		'bootstrap-min' :'web/bootstrap.min',
		'custom-form-elements' :'web/custom-form-elements',
		'jquery-sb' :'web/jquery.sb'		
    },
	  shim: {
        'jQuery': {
            exports: '$'
        },
        'jquery.ui.widget' : {
            exports: 'ui'
        },
        'underscore': {
            exports: '_'
        },
        'handlebar': {
            exports: 'Handlebars'
        },
        'backbone': {
            deps: ['jQuery','lodash','underscore','handlebar'],
            exports: 'Backbone'
        },

        'jquery-transport' : {
            deps: ['jQuery']
        },

        'jquery-file-upload' : {
            deps: ['jquery.ui.widget', 'jquery-transport']
        }

    }
	
    
	
})