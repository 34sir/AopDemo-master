import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class HelloThreeTask extends DefaultTask {
    @Optional
    String message = 'I am 34sir'

    @TaskAction
    def hello() {
        println "hello world $message"
    }
}
