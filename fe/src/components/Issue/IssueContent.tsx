import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as PaperclipIcon } from '../../assets/icon/paperclip.svg';
import { ReactComponent as CaptionIcon } from '../../assets/icon/caption.svg';
import { border, font, radius } from '../../styles/styles';
import { customFetch } from '../../util/customFetch';

interface Props extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
  height: number;
}

type Response = {
  success: boolean;
  data: string;
};

export default function IssueContent({ value, onChange, height }: Props) {
  const theme = useTheme();

  const onAddImgFile = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const imgFile = e.target.files;

    const subUrl = 'api/comments/upload';

    if (imgFile) {
      const response = await customFetch<Response>({
        subUrl: subUrl,
        method: 'POST',
        contentType: 'multipart/form-data',
        body: {
          file: imgFile[0],
        },
      });

      if (response && response.success && response.data) {
        const imaUrl = response.data;
        const updatedValue = value ? `${value}\n\n${imaUrl}\n\n` : imaUrl;

        if (onChange) {
          onChange({
            target: { value: updatedValue },
          } as React.ChangeEvent<HTMLTextAreaElement>);
        }
      }
    }
  };

  return (
    <div css={content(theme, height)}>
      {value && <div className="label">코멘트를 입력하세요</div>}
      <textarea
        className="content"
        placeholder="코멘트를 입력하세요"
        value={value}
        onChange={onChange}
      />
      <div className="caption">
        <CaptionIcon />
        {value ? `띄어쓰기 포함 ${String(value).length}자` : ''}
      </div>
      <label htmlFor="input-file">
        <div className="input-image">
          <PaperclipIcon />
          파일 첨부하기
        </div>
      </label>
      <input
        type="file"
        name="file"
        id="input-file"
        accept="image/gif, image/jpeg, image/png, image/bmp, image/webp"
        onChange={onAddImgFile}
      />
    </div>
  );
}

const content = (theme: Theme, height: number) => css`
  display: flex;
  flex-direction: column;
  width: 100%;
  border-radius: ${radius.large};
  background-color: ${theme.neutral.surfaceBold};

  &:focus-within {
    outline: ${border.default} ${theme.neutral.borderDefaultActive};
    background: ${theme.neutral.surfaceStrong};
  }

  .label {
    padding: 16px 16px 0;
    color: ${theme.neutral.textWeak};
    font: ${font.displayMedium16};
  }

  .content {
    height: ${height}px;
    padding: 16px 16px 0;
    border: none;
    border-radius: ${radius.large} ${radius.large} 0 0;
    background-color: inherit;
    resize: none;
    color: ${theme.neutral.textDefault};
    font: ${font.displayMedium16};

    &::-webkit-scrollbar {
      width: 20px;
    }

    &:focus-within {
      &::-webkit-scrollbar-thumb {
        background-color: ${theme.neutral.textWeak};
        width: 10px;
        border-radius: ${radius.large};
        border: 7px solid ${theme.neutral.surfaceStrong};
      }

      &::-webkit-scrollbar-track {
        background-color: ${theme.neutral.surfaceStrong};
        border-radius: 30px;
      }
    }

    &:focus {
      color: ${theme.neutral.textStrong};
      outline: none;
    }
  }

  .caption {
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
    gap: 8px;
    padding: 0 16px;
    height: 52px;
    border-bottom: ${border.dash} ${theme.neutral.borderDefault};
    color: ${theme.neutral.textWeak};
    font: ${font.availableMedium12};

    svg path {
      stroke: ${theme.neutral.textWeak};
    }
  }

  .input-image {
    display: flex;
    align-items: center;
    gap: 4px;
    height: 52px;
    padding: 0 16px;
    cursor: pointer;
    border-radius: 0 0 ${radius.large} ${radius.large};
    font: ${font.availableMedium12};
    color: ${theme.neutral.textDefault};
  }

  #input-file {
    display: none;
  }
`;
