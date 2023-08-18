import Label from '../Label';
import { ReactComponent as RefreshIcon } from '../../../assets/icon/refreshCcw.svg';
import LabelTextColor from '../LabelTextColor';
import { useContext } from 'react';
import { LabelContext } from '../../Context/LabelContext';
import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../../styles/styles';

export default function LabelCreateInputForm() {
  const theme = useTheme();
  const { ...context } = useContext(LabelContext);

  const setRandomBackground = () => {
    const letters = '0123456789ABCDEF';
    let color = '#';

    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * letters.length)];
    }
    context.setBackgroundColor(color);
  };

  return (
    <div css={labelInputForm(theme)}>
      <div className="label-preview">
        <Label
          title={context.title}
          textColor={context.textColor}
          backgroundColor={context.backgroundColor}
        />
      </div>
      <div className="label-info">
        <div className="title-container">
          <div className="title-info">이름</div>
          <input
            className="title-input"
            type="text"
            value={context.title}
            placeholder="레이블의 이름을 입력하세요"
            onChange={(e) => context.setTitle(e.target.value)}
          />
        </div>
        <div className="description-container">
          <div className="description-info">설명(선택)</div>
          <input
            className="description-input"
            type="text"
            value={context.description}
            placeholder="레이블에 대한 설명을 입력하세요"
            onChange={(e) => context.setDescription(e.target.value)}
          />
        </div>
        <div className="color-container">
          <div className="background-color-container">
            <div className="background-color-info">배경 색상</div>
            <input
              className="background-color-input"
              type="color"
              name="label-background"
              id="color-picker"
              value={context.backgroundColor}
              onChange={(e) => context.setBackgroundColor(e.target.value)}
            />
            <label className="background-color-label" htmlFor="color-picker">
              {context.backgroundColor}
            </label>
            <RefreshIcon
              className="random-color"
              onClick={setRandomBackground}
            />
          </div>
          <LabelTextColor onClick={context.onSelectTextColor} />
        </div>
      </div>
    </div>
  );
}

const labelInputForm = (theme: Theme) => css`
  display: flex;
  gap: 24px;

  .label-preview {
    width: 288px;
    height: 153px;
    border: ${border.default} ${theme.neutral.borderDefault};
    border-radius: ${radius.medium};
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
  }

  .label-info {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 16px;

    .title-container {
      width: auto;
      display: flex;
      align-items: center;
      gap: 8px;
      height: 40px;
      border-radius: ${radius.medium};
      padding: 0 16px;
      background-color: ${theme.neutral.surfaceBold};

      &:focus-within {
        background-color: ${theme.neutral.surfaceStrong};
        outline: ${border.default} ${theme.neutral.borderDefault};
      }

      .title-info {
        width: 64px;
        font: ${font.displayMedium12};
        color: ${theme.neutral.textWeak};
      }

      .title-input {
        flex: 1;
        background-color: inherit;
        border: none;
        font: ${font.displayMedium16};
        color: ${theme.neutral.textWeak};

        &:focus {
          color: ${theme.neutral.textDefault};
          outline: none;
        }
      }
    }

    .description-container {
      display: flex;
      align-items: center;
      gap: 8px;
      height: 40px;
      border-radius: ${radius.medium};
      padding: 0 16px;
      background-color: ${theme.neutral.surfaceBold};

      &:focus-within {
        background-color: ${theme.neutral.surfaceStrong};
        outline: ${border.default} ${theme.neutral.borderDefault};
      }

      .description-info {
        width: 64px;
        font: ${font.displayMedium12};
        color: ${theme.neutral.textWeak};
      }

      .description-input {
        flex: 1;
        background-color: inherit;
        border: none;
        font: ${font.displayMedium16};
        color: ${theme.neutral.textWeak};

        &:focus {
          color: ${theme.neutral.textDefault};
          outline: none;
        }
      }
    }

    .color-container {
      display: flex;
      align-items: center;
      gap: 24px;

      .background-color-container {
        display: flex;
        align-items: center;
        gap: 8px;
        height: 40px;
        border-radius: ${radius.medium};
        padding: 0 16px;
        background-color: ${theme.neutral.surfaceBold};

        .background-color-info {
          width: 64px;
          font: ${font.displayMedium12};
          color: ${theme.neutral.textWeak};
        }

        .background-color-label {
          width: 112px;
          cursor: pointer;
          color: ${theme.neutral.textWeak};
          font: ${font.displayMedium16};
        }

        .background-color-input {
          width: 27px;
          cursor: pointer;
          border: none;
          background-color: inherit;
        }

        .random-color {
          cursor: pointer;
        }
      }
    }
  }
`;
