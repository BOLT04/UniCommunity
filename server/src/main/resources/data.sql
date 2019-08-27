insert into templates(id, name, has_forum, blackboard_names) values
(-3, 'Basic Board', 1, 'Anuncios,Sumarios'),
(-2, 'Basic Board Without Forum', 0, 'Anuncios,Sumarios'),
(-1, 'Advanced Board', 1, 'Anuncios,Sumarios,Bibliografia');

insert into users(id, name, email, pw, role, github_id) values (-1, 'admin', 'admin@gmail.com', 'admin', 'admin', 'gitAdmin')