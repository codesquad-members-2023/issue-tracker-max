import { textColorType } from '../../constant/constant';
import useDropdown from '../../Hook/useDropdown';
import { ReactComponent as ChevronDownIcon } from '../../assets/icon/chevronDown.svg';
import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../styles/styles';
import { useState } from 'react';

type Props = {
  onClick: (color: string) => void;
};

export default function LabelTextColor({ onClick }: Props) {
  const theme = useTheme();
  const [textColorLabel, setTextColorLabel] = useState('텍스트 색상');
  const [selectTextColorIsOpen, selectTextColorRef, setSelectTextColorState] =
    useDropdown(false);

  const onSelectColor = (color: string, name: string) => {
    setTextColorLabel(name);
    onClick(color);
  };

  return (
    <div css={labelTextColor(theme)}>
      <div
        className="text-color-info"
        onClick={setSelectTextColorState}
        ref={selectTextColorRef}
      >
        {textColorLabel}
        <ChevronDownIcon />
        {selectTextColorIsOpen && (
          <div className="text-color-dropdown">
            <div className="text-color-dropdown-info">텍스트 색상 설정</div>
            <ul className="text-color-list">
              {textColorType.map((textColor) => {
                return (
                  <li
                    key={textColor.id}
                    onClick={() =>
                      onSelectColor(textColor.color, textColor.name)
                    }
                  >
                    {textColor.name}
                  </li>
                );
              })}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
}

const labelTextColor = (theme: Theme) => css`
  display: flex;
  align-items: center;
  height: 40px;

  .text-color-info {
    position: relative;
    display: flex;
    align-items: center;
    gap: 4px;
    font: ${font.availableMedium16};
    color: ${theme.neutral.textDefault};
    cursor: pointer;

    .text-color-dropdown {
      position: absolute;
      display: flex;
      flex-direction: column;
      top: 40px;
      left: -15px;
      width: 140px;
      border: ${border.default} ${theme.neutral.borderDefault};
      border-radius: ${radius.large};
      overflow: hidden;
      font: ${font.availableMedium16};
      background-color: ${theme.neutral.surfaceStrong};
      color: ${theme.neutral.textDefault};

      .text-color-dropdown-info {
        padding: 0 16px;
        display: flex;
        align-items: center;
        height: 40px;
        background-color: ${theme.neutral.surfaceDefault};
        font: ${font.displayMedium12};
        color: ${theme.neutral.textWeak};
        cursor: default;
        border-bottom: ${border.default} ${theme.neutral.borderDefault};
      }

      .text-color-list {
        display: flex;
        flex-direction: column;
        align-items: center;

        li {
          width: 100%;
          padding: 0 16px;
          box-sizing: border-box;
          display: flex;
          align-items: center;
          height: 40px;
          border-bottom: ${border.default} ${theme.neutral.borderDefault};

          &:hover {
            opacity: 0.8;
          }

          &:last-child {
            border-bottom: none;
          }
        }
      }
    }
  }
`;
