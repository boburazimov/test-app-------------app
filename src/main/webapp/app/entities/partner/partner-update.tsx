import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGroup } from 'app/shared/model/group.model';
import { getEntities as getGroups } from 'app/entities/group/group.reducer';
import { ISocial } from 'app/shared/model/social.model';
import { getEntities as getSocials } from 'app/entities/social/social.reducer';
import { IPartner } from 'app/shared/model/partner.model';
import { GenderEnum } from 'app/shared/model/enumerations/gender-enum.model';
import { PartnerTypeEnum } from 'app/shared/model/enumerations/partner-type-enum.model';
import { getEntity, updateEntity, createEntity, reset } from './partner.reducer';

export const PartnerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const groups = useAppSelector(state => state.group.entities);
  const socials = useAppSelector(state => state.social.entities);
  const partnerEntity = useAppSelector(state => state.partner.entity);
  const loading = useAppSelector(state => state.partner.loading);
  const updating = useAppSelector(state => state.partner.updating);
  const updateSuccess = useAppSelector(state => state.partner.updateSuccess);
  const genderEnumValues = Object.keys(GenderEnum);
  const partnerTypeEnumValues = Object.keys(PartnerTypeEnum);
  const handleClose = () => {
    props.history.push('/partner' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getGroups({}));
    dispatch(getSocials({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...partnerEntity,
      ...values,
      group: groups.find(it => it.id.toString() === values.group.toString()),
      social: socials.find(it => it.id.toString() === values.social.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          gender: 'MALE',
          partnerType: 'CUSTOMER',
          ...partnerEntity,
          group: partnerEntity?.group?.id,
          social: partnerEntity?.social?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testApp.partner.home.createOrEditLabel" data-cy="PartnerCreateUpdateHeading">
            Create or edit a Partner
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="partner-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Phonenumber"
                id="partner-phonenumber"
                name="phonenumber"
                data-cy="phonenumber"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 13, message: 'This field cannot be longer than 13 characters.' },
                }}
              />
              <ValidatedField
                label="Code"
                id="partner-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                label="First Name"
                id="partner-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Last Name"
                id="partner-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Gender" id="partner-gender" name="gender" data-cy="gender" type="select">
                {genderEnumValues.map(genderEnum => (
                  <option value={genderEnum} key={genderEnum}>
                    {genderEnum}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Age"
                id="partner-age"
                name="age"
                data-cy="age"
                type="text"
                validate={{
                  min: { value: 16, message: 'This field should be at least 16.' },
                  max: { value: 100, message: 'This field cannot be more than 100.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Is Blocked" id="partner-isBlocked" name="isBlocked" data-cy="isBlocked" check type="checkbox" />
              <ValidatedField label="Partner Type" id="partner-partnerType" name="partnerType" data-cy="partnerType" type="select">
                {partnerTypeEnumValues.map(partnerTypeEnum => (
                  <option value={partnerTypeEnum} key={partnerTypeEnum}>
                    {partnerTypeEnum}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Inn"
                id="partner-inn"
                name="inn"
                data-cy="inn"
                type="text"
                validate={{
                  min: { value: 9, message: 'This field should be at least 9.' },
                  max: { value: 9, message: 'This field cannot be more than 9.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Pinfl"
                id="partner-pinfl"
                name="pinfl"
                data-cy="pinfl"
                type="text"
                validate={{
                  min: { value: 14, message: 'This field should be at least 14.' },
                  max: { value: 14, message: 'This field cannot be more than 14.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="partner-group" name="group" data-cy="group" label="Group" type="select">
                <option value="" key="0" />
                {groups
                  ? groups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="partner-social" name="social" data-cy="social" label="Social" type="select">
                <option value="" key="0" />
                {socials
                  ? socials.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/partner" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PartnerUpdate;
