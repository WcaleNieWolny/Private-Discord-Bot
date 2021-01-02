package pl.wolny.cmds.warns;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WarnsCmd extends ListenerAdapter {
     public void GenerateWarns (String id, TextChannel channel, Guild guild, String nick){
         EmbedBuilder builder = new EmbedBuilder();
         builder.setTitle("Warny " + nick);
         builder.setColor(Color.green);
         Dotenv dotenv = Dotenv.load();
        try {
            Connection myConn = DriverManager.getConnection(dotenv.get("JDBC"), dotenv.get("mysql_usr"), dotenv.get("mysql_pass"));
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT idwarna FROM `warns` WHERE idwarnowanego='" + id + "'";
            ResultSet rs = myStmt.executeQuery(sql);
            List <Integer> WarnIds = new ArrayList<>();
            while (rs.next()){
                WarnIds.add(rs.getInt("idwarna"));
            }
            if(WarnIds.size() > 0){
                for(int i=0;i<WarnIds.size();i++){
                    String sql2 = "SELECT powod, nickadmina FROM `warns` WHERE idwarna=" + WarnIds.get(0) + " LIMIT 1";
                    List <String> powod = new ArrayList<>();
                    List <String> nickadmina = new ArrayList<>();
                    ResultSet rs2 = myStmt.executeQuery(sql2);
                    while (rs2.next()){
                        powod.add(rs2.getString("powod"));
                        nickadmina.add(rs2.getString("nickadmina"));
                    }
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                        EmbedBuilder err_1 = new EmbedBuilder();
                        err_1.setTitle("Błąd");
                        err_1.setColor(Color.red);
                        err_1.addField("Kod błędu:", "Błąd wewnętrzny", false);
                        channel.sendMessage(err_1.build()).queue();
                        return;
                    }
                    if(nickadmina.size() > 0 && powod.size() > 0){
                        String value = "Admin: " + nickadmina.get(0) + "\n" + "Powód: " + powod.get(0);
                        builder.addField("Warn o id " + WarnIds.get(i), value, false);
                    }else {
                        EmbedBuilder err_1 = new EmbedBuilder();
                        err_1.setTitle("Błąd");
                        err_1.setColor(Color.red);
                        err_1.addField("Kod błędu:", "Błąd wewnętrzny", false);
                        channel.sendMessage(err_1.build()).queue();
                        return;
                    }
                }
                channel.sendMessage(builder.build()).queue();
            }else {
                builder.setDescription("Taki gracz nie ma warnów!");
                channel.sendMessage(builder.build()).queue();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            EmbedBuilder err_1 = new EmbedBuilder();
            err_1.setTitle("Błąd");
            err_1.setColor(Color.red);
            err_1.addField("Kod błędu:", "Błąd wewnętrzny", false);
            channel.sendMessage(err_1.build()).queue();
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
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        if(msg[0].equalsIgnoreCase("?warns")){
            Member user = event.getGuild().getMemberById(event.getAuthor().getId());
                if(msg.length > 1){
                    assert user != null;
                    if(user.hasPermission(Permission.MESSAGE_MANAGE)){
                        if(event.getMessage().getMentionedMembers().size()>0){
                            Member usr = event.getMessage().getMentionedMembers().get(0);
                            GenerateWarns(usr.getId(), event.getChannel(), event.getGuild(), usr.getUser().getName());
                        }else {
                            if (isNumeric(msg[1])) {
                                GenerateWarns(msg[1], event.getChannel(), event.getGuild(), msg[1]);
                            }else {
                                GenerateWarns(event.getAuthor().getId(), event.getChannel(), event.getGuild(), event.getAuthor().getName());
                            }
                        }
                    }else {
                        GenerateWarns(event.getAuthor().getId(), event.getChannel(), event.getGuild(), event.getAuthor().getName());
                    }
                }else {
                    GenerateWarns(event.getAuthor().getId(), event.getChannel(), event.getGuild(), event.getAuthor().getName());
                }
        }
    }
}
