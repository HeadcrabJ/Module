package eu.u032.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvalCommand extends Command {
    public EvalCommand() {
        this.name = "eval";
        this.ownerCommand = true;
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.async(() -> {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.put("event", event);
            try {
                event.replySuccess("Evaluated Successfully:\n```" + engine.eval(event.getArgs()) + "```");
            } catch (Exception e) {
                event.replyError(e.getMessage());
            }
        });
    }
}
