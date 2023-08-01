import { Icon } from 'components/Icon/Icon';
import { Button } from 'components/Button/Button';

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
      <div>
        <div style={{ display: 'flex' }}>
          <Button size="M" variant="ghost">
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="contained">
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="contained" disabled>
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="outline">
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
        </div>

        <div style={{ display: 'flex' }}>
          <Button size="M" variant="outline" disabled>
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="ghost" disabled>
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="ghost">
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="ghost" states="selected">
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="ghost" states="danger">
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
          <Button size="M" variant="ghost" states="selected">
            <Icon icon="Plus" size="M" />
            닫힌 이슈
          </Button>
        </div>
        {/* <div style={{ display: 'flex' }}>
          <Button buttonType="contained" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="outline" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="ghost" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
        </div>
        <div style={{ display: 'flex' }}>
          <Button buttonType="contained" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="outline" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="ghost" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
        </div>
        <div style={{ display: 'flex' }}>
          <Button buttonType="contained" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="outline" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="ghost" size="L">
            <Icon icon="Plus" size="L" />
            <p>버튼1</p>
          </Button>
        </div> */}
      </div>
      {/* <div>
        <div style={{ display: 'flex' }}>
          <Button buttonType="contained" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="outline" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="ghost" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
        </div>
        <div style={{ display: 'flex' }}>
          <Button buttonType="contained" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="outline" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="ghost" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
        </div>
        <div style={{ display: 'flex' }}>
          <Button buttonType="contained" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="outline" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="ghost" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
        </div>
        <div style={{ display: 'flex' }}>
          <Button buttonType="contained" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="outline" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
          <Button buttonType="ghost" size="M">
            <Icon icon="Plus" size="M" />
            <p>버튼1</p>
          </Button>
        </div>
      </div>
      <div style={{ display: 'flex' }}>
        <Button buttonType="contained" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="outline" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="ghost" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
      </div>
      <div style={{ display: 'flex' }}>
        <Button buttonType="contained" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="outline" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="ghost" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
      </div>
      <div style={{ display: 'flex' }}>
        <Button buttonType="contained" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="outline" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="ghost" size="S">
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
      </div>
      <div style={{ display: 'flex' }}>
        <Button buttonType="contained" size="S" disabled>
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="outline" size="S" disabled>
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
        <Button buttonType="ghost" size="S" disabled>
          <Icon icon="Plus" size="S" />
          <p>버튼1</p>
        </Button>
      </div> */}
    </div>
  );
};
