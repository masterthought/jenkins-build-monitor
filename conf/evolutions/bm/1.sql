# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table build_job (
  id                        bigint not null,
  name                      varchar(255),
  build_number              varchar(255),
  hidden                    boolean,
  highlight                 boolean,
  display_order             bigint,
  BUILD_MONITOR_CONFIG      bigint,
  constraint pk_build_job primary key (id))
;

create table build_monitor_config (
  id                        bigint not null,
  name                      varchar(255),
  build_url                 varchar(255),
  constraint pk_build_monitor_config primary key (id))
;

create sequence build_job_seq;

create sequence build_monitor_config_seq;

alter table build_job add constraint fk_build_job_buildMonitorConfi_1 foreign key (BUILD_MONITOR_CONFIG) references build_monitor_config (id) on delete restrict on update restrict;
create index ix_build_job_buildMonitorConfi_1 on build_job (BUILD_MONITOR_CONFIG);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists build_job;

drop table if exists build_monitor_config;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists build_job_seq;

drop sequence if exists build_monitor_config_seq;

