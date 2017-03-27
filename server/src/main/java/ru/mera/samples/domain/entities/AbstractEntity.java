package ru.mera.samples.domain.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.TableGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue( generator = "id_seq_generator" )
  @GenericGenerator( name = "id_seq_generator", strategy = "org.hibernate.id.enhanced.TableGenerator", parameters = {
      @Parameter( name = TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true" ) } )
  @Column
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
