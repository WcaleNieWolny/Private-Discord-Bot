package pl.wolny.cmds.warns;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.*;
import java.util.EnumSet;

public class WarnCmd extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        if (msg[0].equalsIgnoreCase("?warn")) {
            Member user = event.getGuild().getMemberById(event.getAuthor().getId());
                assert user != null;
                if(user.hasPermission(Permission.MESSAGE_MANAGE)){
                    if(msg.length > 2){
                        if(pl.wolny.cmds.warns.WarnsCmd.isNumeric(msg[1])){
                            Member warn_new = event.getGuild().getMemberById(msg[1]);
                            if(warn_new != null){
                                Connection myConn = null;
                                StringBuilder sb = new StringBuilder();
                                for (int i = 2; i < msg.length; i++) {
                                    sb.append(msg[i]).append(" ");
                                }
                                String reason = sb.toString().trim();
                                System.out.print(reason);
                                try {
                                    Dotenv dotenv = Dotenv.load();
                                    myConn = DriverManager.getConnection(dotenv.get("JDBC"), dotenv.get("mysql_usr"), dotenv.get("mysql_pass"));
                                    Statement myStmt = myConn.createStatement();
                                    String sql = "INSERT INTO `warns` (`idwarna`, `idwarnowanego`, `nickwarnownego`, `idadmina`, `nickadmina`, `powod`) VALUES (NULL, '" + msg[1] + "', '" + warn_new.getUser().getName() + "', '" + event.getAuthor().getId() + "', '" + event.getAuthor().getName() +"', '" + reason + "')";
                                    myStmt.executeUpdate(sql);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                    EmbedBuilder err_1 = new EmbedBuilder();
                                    err_1.setTitle("Błąd");
                                    err_1.setColor(Color.red);
                                    err_1.addField("Kod błędu:", "Błąd wewnętrzny", false);
                                    event.getChannel().sendMessage(err_1.build()).queue();
                                    return;
                                }
                                EmbedBuilder suk = new EmbedBuilder();
                                suk.setTitle("Pomyślnie dodano ostrzeżenie");
                                suk.setColor(Color.green);
                                suk.addField("Osoba ostrzeżona:", warn_new.getUser().getName(), false);
                                suk.addField("Admin:", event.getAuthor().getName(), false);
                                suk.addField("Powód:", reason, false);
                                event.getChannel().sendMessage(suk.build()).queue();
                            }else {
                                EmbedBuilder perms = new EmbedBuilder();
                                perms.setTitle("Błąd");
                                perms.setColor(Color.red);
                                perms.addField("Kod błędu:", "Taki użytkownik nie istnieje", false);
                                event.getChannel().sendMessage(perms.build()).queue();
                            }

                        }else {
                            if(event.getMessage().getMentionedMembers().size() > 0){
                                Member warn_new = event.getGuild().getMemberById(event.getMessage().getMentionedMembers().get(0).getId());
                                if(warn_new != null){
                                    Connection myConn = null;
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 2; i < msg.length; i++) {
                                        sb.append(msg[i]).append(" ");
                                    }
                                    String reason = sb.toString().trim();
                                    System.out.print(reason);
                                    try {
                                        Dotenv dotenv = Dotenv.load();
                                        myConn = DriverManager.getConnection(dotenv.get("JDBC"), dotenv.get("mysql_usr"), dotenv.get("mysql_pass"));
                                        Statement myStmt = myConn.createStatement();
                                        String sql = "INSERT INTO `warns` (`idwarna`, `idwarnowanego`, `nickwarnownego`, `idadmina`, `nickadmina`, `powod`) VALUES (NULL, '" + warn_new.getId() + "', '" + warn_new.getUser().getName() + "', '" + event.getAuthor().getId() + "', '" + event.getAuthor().getName() +"', '" + reason + "')";
                                        myStmt.executeUpdate(sql);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                        EmbedBuilder err_1 = new EmbedBuilder();
                                        err_1.setTitle("Błąd");
                                        err_1.setColor(Color.red);
                                        err_1.addField("Kod błędu:", "Błąd wewnętrzny", false);
                                        event.getChannel().sendMessage(err_1.build()).queue();
                                        return;
                                    }
                                    EmbedBuilder suk = new EmbedBuilder();
                                    suk.setTitle("Pomyślnie dodano ostrzeżenie");
                                    suk.setColor(Color.green);
                                    suk.addField("Osoba ostrzeżona:", warn_new.getUser().getName(), false);
                                    suk.addField("Admin:", event.getAuthor().getName(), false);
                                    suk.addField("Powód:", reason, false);
                                    event.getChannel().sendMessage(suk.build()).queue();
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
}
