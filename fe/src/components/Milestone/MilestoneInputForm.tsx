import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../styles/styles';
import { getTodayDate } from '../../util/getTodayDate';
import { getFutureDate } from '../../util/getFutureDate';

type Props = {
  title: string;
  description?: string;
  dueDate?: string;
  onChangeTitle: (title: string) => void;
  onChangeDescription: (description: string) => void;
  onChangeDueDate: (dueDate: string) => void;
};

export default function MilestoneInputForm({
  title,
  description,
  dueDate,
  onChangeTitle,
  onChangeDescription,
  onChangeDueDate,
}: Props) {
  const theme = useTheme();

  return (
    <div css={milestoneInputForm(theme)}>
      <div className="milestone-info">
        <div className="milestone-title-date">
          <div className="title-container">
            <div className="title-info">이름</div>
            <input
              className="title-input"
              type="text"
              value={title}
              placeholder="마일스톤의 이름을 입력하세요"
              onChange={(e) => onChangeTitle(e.target.value)}
            />
          </div>
          <div className="date-container">
            <div className="date-info">완료일(선택)</div>
            <input
              className="date-input"
              type="date"
              value={dueDate}
              min={getTodayDate()}
              max={getFutureDate(10)}
              onChange={(e) => onChangeDueDate(e.target.value)}
            />
          </div>
        </div>
        <div className="description-container">
          <div className="description-info">설명(선택)</div>
          <input
            className="description-input"
            type="text"
            value={description}
            placeholder="마일스톤에 대한 설명을 입력하세요"
            onChange={(e) => onChangeDescription(e.target.value)}
          />
        </div>
      </div>
    </div>
  );
}

const milestoneInputForm = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 24px;

  .milestone-info {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 16px;

    .milestone-title-date {
      display: flex;
      gap: 16px;

      .title-container {
        flex: 1;
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

      .date-container {
        flex: 1;
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

        .date-info {
          width: 64px;
          font: ${font.displayMedium12};
          color: ${theme.neutral.textWeak};
        }

        .date-input {
          flex: 1;
          background-color: inherit;
          border: none;
          font: ${font.displayMedium16};
          color: ${theme.neutral.textWeak};
          position: relative;

          &:focus {
            color: ${theme.neutral.textDefault};
            outline: none;
          }

          &::-webkit-calendar-picker-indicator {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background: transparent;
            color: transparent;
            cursor: pointer;
          }

          &::
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
  }
`;
