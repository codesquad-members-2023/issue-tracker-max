import { Icon } from 'components/Icon';

export const ComponentTest = () => {
  return (
    <div>
      <div style={{ display: 'flex' }}>
        <Icon icon="alertCircle" />
        <Icon icon="alertCircle" stroke="paletteBlue" />
        <Icon icon="archive" stroke="paletteBlue" />
        <Icon icon="calendar" stroke="paletteBlue" />
        <Icon icon="checkBoxActive" stroke="paletteBlue" />
        <Icon icon="checkBoxDisable" stroke="paletteBlue" />
        <Icon icon="checkBoxInitial" stroke="paletteBlue" />
        <Icon icon="checkOffCircle" stroke="paletteBlue" />
        <Icon icon="checkOnCircle" stroke="brandSurfaceDefault" />
        <Icon icon="checkOnCircle" stroke="brandTextDefault" />
        <Icon icon="chevronDown" />
        <Icon icon="edit" stroke="brandTextWeak" />
        <Icon icon="label" />
        <Icon icon="paperclip" />
        <Icon icon="label" />
        <Icon icon="plus" size="L" />
        <Icon icon="plus" size="L" stroke="nuetralTextStrong" />
        <Icon icon="plus" size="L" stroke="brandTextDefault" />
        <Icon
          icon="plus"
          size="S"
          stroke="brandTextWeak"
          fill="nuetralTextStrong"
        />
      </div>
      <div style={{ display: 'flex' }}>
        <Icon icon="refreshCcw" />
        <Icon icon="search" />
        <Icon icon="smile" />
        <Icon icon="trash" stroke="dangerTextDefault" />
        <Icon
          icon="userImageLargeIcon"
          fill="nuetralSurfaceBold"
          stroke="nuetralSurfaceBold"
        />
        <Icon
          icon="userImageSmallIcon"
          fill="nuetralSurfaceBold"
          stroke="nuetralSurfaceBold"
        />
        <Icon icon="xSquare" stroke="brandTextWeak" />
        <Icon icon="xSquare" />
        <Icon icon="light" fill="nuetralTextDefault" />
        <Icon icon="dark" fill="nuetralTextDefault" />
      </div>
    </div>
  );
};
