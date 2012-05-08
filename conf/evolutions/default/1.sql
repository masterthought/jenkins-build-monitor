# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table build_job (
  id                        bigint not null,
  name                      varchar(1024),
  url                       varchar(1024),
  color                     varchar(255),
  build_number              varchar(255),
  hidden                    boolean,
  highlight                 boolean,
  in_progress               boolean,
  display_order             integer,
  constraint pk_build_job primary key (id))
;

create table build_monitor_config (
  id                        bigint not null,
  name                      varchar(255),
  build_url                 varchar(255),
  background_color          varchar(255),
  constraint pk_build_monitor_config primary key (id))
;


create table build_monitor_config_build_job (
  build_monitor_config_id        bigint not null,
  build_job_id                   bigint not null,
  constraint pk_build_monitor_config_build_job primary key (build_monitor_config_id, build_job_id))
;
create sequence build_job_seq;

create sequence build_monitor_config_seq;




alter table build_monitor_config_build_job add constraint fk_build_monitor_config_build_01 foreign key (build_monitor_config_id) references build_monitor_config (id) on delete restrict on update restrict;

alter table build_monitor_config_build_job add constraint fk_build_monitor_config_build_02 foreign key (build_job_id) references build_job (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists build_job;

drop table if exists build_monitor_config_build_job;

drop table if exists build_monitor_config;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists build_job_seq;

drop sequence if exists build_monitor_config_seq;

