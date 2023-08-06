import { css } from '@emotion/react';

interface Props extends React.HTMLAttributes<HTMLInputElement> {
  checked?: boolean;
}

export default function CheckBoxIcon({ id = '', checked, onChange }: Props) {
  return (
    <div css={checkBoxIcon(id)}>
      <input
        id={id}
        className="check-box"
        type="checkbox"
        onChange={onChange}
        checked={checked}
      ></input>
      <label htmlFor={id} className="label" />
    </div>
  );
}

const checkBoxIcon = (id: string) => css`
  position: relative;

  .check-box {
    appearance: none;
    width: 16px;
    height: 16px;
    margin: 0;
    background-image: url('/src/assets/icon/checkBoxInitial.svg');
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position: 50%;
    cursor: pointer;

    &:checked {
      background-image: ${id === 'selectAll'
        ? `url('/src/assets/icon/checkBoxDisable.svg')`
        : `url('/src/assets/icon/checkBoxActive.svg')`};
    }
  }

  .label {
    position: absolute;
    top: -30%;
    left: -30%;
    width: 40px;
    height: ${id === 'selectAll' ? '40px' : '70px'};
    cursor: pointer;
  }
`;
