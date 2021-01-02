package pl.wolny.cmds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AvatarCmd extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args.length>0){
            if(args[0].equals("?av") || (args[0].equals("?avatar"))){
                if(args.length > 1){
                    if(event.getMessage().getMentionedMembers().size() > 1){
                        GenerateAvatar(event.getChannel(), event.getMessage().getMentionedUsers().get(0));
                    }else {
                        if(isNumeric(args[1])){
                            Member user = event.getGuild().getMemberById(args[1]);
                            if (user != null) {
                                GenerateAvatar(event.getChannel(), user.getUser());
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
                    }
                }else {
                    GenerateAvatar(event.getChannel(), event.getAuthor());
                }
            }
        }
    }
    private void GenerateAvatar(TextChannel channel, User user){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Avatar");
        embedBuilder.setColor(Color.green);
        embedBuilder.setImage(user.getAvatarUrl());
        embedBuilder.setFooter(user.getAsTag(), user.getAvatarUrl());
        channel.sendMessage(embedBuilder.build()).queue();
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
