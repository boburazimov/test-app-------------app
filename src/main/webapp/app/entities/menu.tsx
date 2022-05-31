import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/company">
        Company
      </MenuItem>
      <MenuItem icon="asterisk" to="/position">
        Position
      </MenuItem>
      <MenuItem icon="asterisk" to="/partner">
        Partner
      </MenuItem>
      <MenuItem icon="asterisk" to="/group">
        Group
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        Address
      </MenuItem>
      <MenuItem icon="asterisk" to="/district">
        District
      </MenuItem>
      <MenuItem icon="asterisk" to="/city">
        City
      </MenuItem>
      <MenuItem icon="asterisk" to="/region">
        Region
      </MenuItem>
      <MenuItem icon="asterisk" to="/social">
        Social
      </MenuItem>
      <MenuItem icon="asterisk" to="/country">
        Country
      </MenuItem>
      <MenuItem icon="asterisk" to="/warehouse">
        Warehouse
      </MenuItem>
      <MenuItem icon="asterisk" to="/price-type">
        Price Type
      </MenuItem>
      <MenuItem icon="asterisk" to="/currency">
        Currency
      </MenuItem>
      <MenuItem icon="asterisk" to="/attachment">
        Attachment
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        Product
      </MenuItem>
      <MenuItem icon="asterisk" to="/brand">
        Brand
      </MenuItem>
      <MenuItem icon="asterisk" to="/category">
        Category
      </MenuItem>
      <MenuItem icon="asterisk" to="/unit">
        Unit
      </MenuItem>
      <MenuItem icon="asterisk" to="/barcode">
        Barcode
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
