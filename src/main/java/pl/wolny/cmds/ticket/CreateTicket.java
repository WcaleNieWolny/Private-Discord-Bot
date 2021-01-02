package pl.wolny.cmds.ticket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.swing.plaf.ColorUIResource;
import java.util.EnumSet;
import java.util.Objects;

public class CreateTicket extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        if(msg[0].equalsIgnoreCase("?gentickmsg")){
            if(Objects.requireNonNull(event.getGuild().getMemberById(event.getAuthor().getId())).hasPermission(Permission.ADMINISTRATOR)){
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(ColorUIResource.CYAN);
                embedBuilder.setTitle("Kliknij \uD83D\uDCE7 poniżej aby utworzyć ticket");
                embedBuilder.addField("UWAGA!", "TWORZENIE WIELU TICKETÓW DOTYCZĄCYCH TEGO SAMEGO SKUTKUJE WARNEM!", false);
                event.getChannel().sendMessage(embedBuilder.build()).queue(message -> {
                    message.addReaction("\uD83D\uDCE7").queue();
                });

            }
        }
    }
}
