package pl.wolny.cmds.ticket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.EnumSet;
import java.util.Objects;

public class AddUserToTicket extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        if(msg[0].equalsIgnoreCase("?tickadd")){
            if(Objects.requireNonNull(event.getGuild().getMemberById(event.getAuthor().getId())).hasPermission(Permission.MESSAGE_MANAGE)){
                if(event.getChannel().getName().contains("ticket-")){
                    if(msg.length > 1){
                        if(pl.wolny.cmds.warns.WarnsCmd.isNumeric(msg[1])){
                            Member user = event.getGuild().getMemberById(msg[1]);
                            if (user != null) {
                                event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0).getManager().putPermissionOverride(user, EnumSet.of(Permission.VIEW_CHANNEL), null).queue();
                                EmbedBuilder embedBuilder = new EmbedBuilder();
                                embedBuilder.setTitle("Sukces!");
                                embedBuilder.setColor(Color.green);
                                event.getChannel().sendMessage(embedBuilder.build()).queue();
                                event.getChannel().sendMessage(user.getAsMention()).queue();
                            }else {
                                EmbedBuilder perms = new EmbedBuilder();
                                perms.setTitle("Błąd");
                                perms.setColor(Color.red);
                                perms.addField("Kod błędu:", "Taki użytkownik nie istnieje", false);
                                event.getChannel().sendMessage(perms.build()).queue();
                            }
                        }else {
                            EmbedBuilder perms = new EmbedBuilder();
                            perms.setTitle("Błąd");
                            perms.setColor(Color.red);
                            perms.addField("Kod błędu:", "Taki użytkownik nie istnieje", false);
                            event.getChannel().sendMessage(perms.build()).queue();
                        }
                    }else {
                        EmbedBuilder perms = new EmbedBuilder();
                        perms.setTitle("Błąd");
                        perms.setColor(Color.red);
                        perms.addField("Kod błędu:", "Złe użycie", false);
                        event.getChannel().sendMessage(perms.build()).queue();
                    }
                }else {
                    EmbedBuilder perms = new EmbedBuilder();
                    perms.setTitle("Błąd");
                    perms.setColor(Color.red);
                    perms.addField("Kod błędu:", "Zły kanał", false);
                    event.getChannel().sendMessage(perms.build()).queue();
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
}
