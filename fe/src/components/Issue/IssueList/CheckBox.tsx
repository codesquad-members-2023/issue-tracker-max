import { css } from '@emotion/react';

interface Props extends React.HTMLAttributes<HTMLInputElement> {
  id: 'selectAll' | 'selectOne';
  checked: boolean;
}

export default function CheckBoxIcon({ id, checked, onChange }: Props) {
  return (
    <div css={checkBoxIcon(id)}>
      <input
        id={id}
        className="check-box"
        type="checkbox"
        onChange={onChange}
        checked={checked}
      />
      <label htmlFor={id} className="label" />
    </div>
  );
}

const checkBoxIcon = (id: 'selectAll' | 'selectOne') => css`
  position: relative;

  .check-box {
    appearance: none;
    width: 16px;
    height: 16px;
    margin: 0;
    background-image: url('../../../../src/assets/icon/checkBoxInitial.svg');
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position: 50%;
    cursor: pointer;

    &:checked {
      background-image: ${CHECK_BOX_STYLE[id].backgroundImage};
    }
  }

  .label {
    position: absolute;
    top: -30%;
    left: -30%;
    width: 40px;
    height: ${CHECK_BOX_STYLE[id].height};
    cursor: pointer;
  }
`;

const SELECT_ALL_SVG = `url('../../../../src/assets/icon/checkBoxDisable.svg')`;
const SELECT_ONE_SVG = `url('../../../../src/assets/icon/checkBoxActive.svg')`;
const ALL_LABEL_HEIGHT = '40px';
const ONE_LABEL_HEIGHT = '70px';

const CHECK_BOX_STYLE = {
  selectAll: {
    backgroundImage: SELECT_ALL_SVG,
    height: ALL_LABEL_HEIGHT,
  },
  selectOne: {
    backgroundImage: SELECT_ONE_SVG,
    height: ONE_LABEL_HEIGHT,
  },
};
