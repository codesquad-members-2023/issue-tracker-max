import { Icon } from 'components/Icon';

export const ComponentTest = () => {
  return (
    <div>
      <div style={{ display: 'flex' }}>
        <Icon icon="AlertCircle" />
        <Icon icon="AlertCircle" stroke="paletteBlue" />
        <Icon icon="Archive" stroke="paletteBlue" />
        <Icon icon="Calendar" stroke="paletteBlue" />
        <Icon icon="CheckBoxActive" stroke="paletteBlue" />
        <Icon icon="CheckBoxDisable" stroke="paletteBlue" />
        <Icon icon="CheckBoxInitial" stroke="paletteBlue" />
        <Icon icon="CheckOffCircle" stroke="paletteBlue" />
        <Icon icon="CheckOnCircle" stroke="brandSurfaceDefault" />
        <Icon icon="CheckOnCircle" stroke="brandTextDefault" />
        <Icon icon="ChevronDown" />
        <Icon icon="Edit" stroke="brandTextWeak" />
        <Icon icon="Label" />
        <Icon icon="Paperclip" />
        <Icon icon="Label" />
        <Icon icon="Plus" size="L" />
        <Icon icon="Plus" size="L" stroke="nuetralTextStrong" />
        <Icon icon="Plus" size="L" stroke="brandTextDefault" />
        <Icon
          icon="Plus"
          size="S"
          stroke="brandTextWeak"
          fill="nuetralTextStrong"
        />
      </div>
      <div style={{ display: 'flex' }}>
        <Icon icon="RefreshCcw" />
        <Icon icon="Search" />
        <Icon icon="Smile" />
        <Icon icon="Trash" stroke="dangerTextDefault" />
        <Icon
          icon="UserImageLarge"
          fill="nuetralSurfaceBold"
          stroke="nuetralSurfaceBold"
        />
        <Icon
          icon="UserImageSmall"
          fill="nuetralSurfaceBold"
          stroke="nuetralSurfaceBold"
        />
        <Icon icon="XSquare" stroke="brandTextWeak" />
        <Icon icon="XSquare" />
        <Icon icon="Light" fill="nuetralTextDefault" />
        <Icon icon="Dark" fill="nuetralTextDefault" />
      </div>
    </div>
  );
};
