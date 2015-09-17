module.exports = function(grunt) {
    grunt.initConfig({
        shell: {
            server: {
                command: 'java -cp L1.2-1.0-jar-with-dependencies.jar main.Main 8080'
            }
        },
        fest: {
            templates: { /* Цель */

                files: [{
                    expand: true,
                    cwd: 'templates', /* исходная директория */
                    src: '*.xml', /* имена шаблонов */
                    dest: 'public_html/js/tmpl' /* результирующая директория */}],

                options: {

                    template: function (data) { /* формат функции-шаблона */

                        return grunt.template.process(
                        /* присваиваем функцию-шаблон переменной */
                            'var <%= name %>Tmpl = <%= contents %> ;',
                            {data: data}

                        );

                    }

                }

            }
        },
        concat: {
            options: { separator: '\n\n;\n\n' },
            foo: {
                options: {  },
                src: ['style/js/underscore-min.js',
                    'style/js/backbone-min.js',
                    'style/js/require.min.js',
                    'style/js/jquery-1.11.3.min.js'],
                dest: 'style/js/main.js'

            },
            bar: { /* Цель bar */ }
        },
        any_other_name: 'hello' /* Любое произвольное свойство */
    });

// Загрузка плагинов, на примере "concat".
grunt.loadNpmTasks('grunt-contrib-concat');
grunt.loadNpmTasks('grunt-shell');
grunt.loadNpmTasks('grunt-fest');

// Определение задач, default должен быть всегда.
grunt.registerTask('default', ['concat']);
};
