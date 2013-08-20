require.config({
	waitSeconds:50,
    paths: {
        'jQuery'	: '../lib/jquery-1.9.1.min',
        'jQueryUI'	: '../lib/jquery-ui-1.9.2.min',
		'json2'		: '../lib/backbone/json2',
		'underscore': '../lib/backbone/underscore-min',
		'handlebar'	: '../lib/backbone/handlebar',
		'backbone'	: '../lib/backbone/backbone-min',
		'datepicker':'../lib/bootstrap-datepicker',
		'templates' : '../../templates',
		'webRoot'	: '../web',
		'webViews'	: '../views/web',
		'models'		: '../models',
		'collections'		: '../collections',
		'crossper-util'	: '../app/crossper-utils',
		'text'		: '../text',
        'jquery.ui.widget' : '../lib/jquery-ui-core1.9.2',
        'jquery-transport' : '../lib/jquery.iframe-transport',
        'jquery-file-upload' : '../lib/jquery.fileupload',
        'custom-form-elements' :'../web/custom-form-elements',
        'bootstrap-min' :'../web/bootstrap.min',
        'jquery-sb' :'../web/jquery.sb',
        'typeahead'	:'../lib/typeahead'
    },
    shim: {
        'jQuery': {
            exports: '$'
        },
		'jQueryUI': {
		deps:['jQuery']
		},
        'underscore': {
            exports: '_'
        },
		'handlebar': {
            exports: 'Handlebars'
        },
		'backbone': {
			deps: ['jQuery','underscore','handlebar'],
            exports: 'Backbone'
        }

    }
});


require(['jQuery',
         'underscore',
         'handlebar',
         'backbone',
         'webRoot/router',
         'webViews/main'
     ], 
		function ($,_,Handlebars,Backbone,CrossperRouter,MainView) {

	//var crossperRouter=new CrossperRouter();
	
	new MainView();
 
});