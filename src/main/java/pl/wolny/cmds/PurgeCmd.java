package pl.wolny.cmds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PurgeCmd extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] messageString = event.getMessage().getContentRaw().split(" ");
        if (messageString[0].toString().equals("?purge")) {
            Member user = event.getGuild().getMemberById(event.getAuthor().getId());
            if (user.hasPermission(Permission.MESSAGE_MANAGE)) {
                if (messageString.length == 2) {
                    if (isNumeric(messageString[1].toString())) {
                        if(Integer.parseInt(messageString[1]) > 1){
                            event.getMessage().delete().queue();
                            List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(messageString[1])).complete();
                            event.getChannel().deleteMessages(messages).queue();
                            event.getChannel().deleteMessages(messages).complete();
                            EmbedBuilder sukcess = new EmbedBuilder();
                            sukcess.setTitle("Sukces");
                            sukcess.setColor(Color.green);
                            sukcess.addField("Usuniętę wiadomości:", messageString[1], false);
                            event.getChannel().sendMessage(sukcess.build()).queueAfter(1, TimeUnit.SECONDS);
                        } else {
                            EmbedBuilder Int = new EmbedBuilder();
                            Int.setTitle("Błąd");
                            Int.setColor(Color.red);
                            Int.addField("Kod błędu:", "Liczba wiadmości musi być większa niż 1", false);
                            event.getChannel().sendMessage(Int.build()).queue();
                        }
                    }else {
                        EmbedBuilder Int = new EmbedBuilder();
                        Int.setTitle("Błąd");
                        Int.setColor(Color.red);
                        Int.addField("Kod błędu:", "Zła liczba", false);
                        event.getChannel().sendMessage(Int.build()).queue();
                    }
                }else {
                    EmbedBuilder usg = new EmbedBuilder();
                    usg.setTitle("Błąd");
                    usg.setColor(Color.red);
                    usg.addField("Kod błędu:", "Złe użycie!", false);
                    event.getChannel().sendMessage(usg.build()).queue();
                }
            }else {
                EmbedBuilder perms = new EmbedBuilder();
                perms.setTitle("Błąd");
                perms.setColor(Color.red);
                perms.addField("Kod błędu:", "Brak permisji", false);
                event.getChannel().sendMessage(perms.build()).queue();
            }
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
