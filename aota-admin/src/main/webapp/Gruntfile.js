module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        jshint: {
            jshintrc: true,
            all: ['Gruntfile.js', 'scripts/**.js', '!scripts/**.min.js']
        },

        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> */\n',
                mangle: false, 
                preserveComments: 'some',
                report: 'min',
                // footer:'\n/*! <%= pkg.name %> Last Modify at： <%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %> */'
            },
            dist: {
                files: [{
                    expand: true,
                    cwd: 'scripts',
                    src: ['*.js', '!*.min.js'],
                    dest: 'scripts',
                    ext: '.min.js'
                },
                {
                    expand: true,
                    cwd: 'bower_components/chart-js',
                    src: ['*.js', '!*.min.js'],
                    dest: 'bower_components/chart-js',
                    ext: '.min.js'
                }]
            }
        },

        cssmin: {
            options: {
                banner: '/*! <%= pkg.name %> */'
            },

            minify: {
                files: [{
                    expand: true,
                    cwd: 'styles',
                    src: ['*.css', '!*.min.css'],
                    dest: 'styles',
                    ext: '.min.css'
                }]
            }
        }
    });

    // Load the plugin
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');

    // Default task(s).
    grunt.registerTask('default', ['uglify', 'cssmin']);
};