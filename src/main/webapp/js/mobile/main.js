require.config({
	waitSeconds:50,
    paths: {
    	 'jQuery'	: '../lib/jquery-1.8.2.min',
         'jquerymobile':'../mobile/jquery.mobile-1.2.0',
         'commomjs'	: '../mobile/common-js',
         'datebox'	: '../mobile/jqm-datebox-1.1.0.core',
         'calbox'	: '../mobile/jqm-datebox-1.1.0.mode.calbox',
         'json2'		: '../lib/backbone/json2',
         'underscore': '../lib/backbone/underscore-min',
         'handlebar'	: '../lib/backbone/handlebar',
         'lodash'	: '../lib/backbone/lodash',
         'backbone'	: '../lib/backbone/backbone-min',
         'templates' : '../../templates',
         'mobileRoot'	: '../mobile',
         'mobileViews'	: '../views/mobile',
         'models'		: '../models',
         'collections'	: '../collections',
         'crossper-util'	: '../app/crossper-utils',
         'text'			: '../text',
         'jquery.ui.widget' : '../lib/jquery-ui-core1.9.2',
         'jquery-transport' : '../lib/jquery.iframe-transport',
         'jquery-file-upload' : '../lib/jquery.fileupload'
    },
    shim: {
        'jQuery': {
            exports: '$'
        },
        'jquery-ui' : {
            exports: 'jquery.ui.widget'
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

        'datebox': {
            deps: ['jquerymobile']
        },

        'calbox': {
            deps: ['datebox']
        },

        'jquery-transport' : {
            deps: ['jQuery']
        },

        'jquery-file-upload' : {
            deps: ['jquery.ui.widget', 'jquery-transport']
        }


    }
});
require(['jQuery','underscore','handlebar','backbone','mobileRoot/router'],
    function ($,_,Handlebars,Backbone,CrossperMobileRouter) {


        $( document ).on( "mobileinit",
            function() {
                // Prevents all anchor click handling including the addition of active button state and alternate link bluring.
                $.mobile.linkBindingEnabled = false;

                // Disabling this will prevent jQuery Mobile from handling hash changes
                $.mobile.hashListeningEnabled = false;

                $.mobile.pushStateEnabled=false;
            }
        )

        require( [ "jquerymobile" ], function() {
            // Instantiates a new Backbone.js Mobile Router
            this.router = new CrossperMobileRouter();

            Backbone.history.start({pushState: true,root: "/crossper/mobile/"});

        });

    });