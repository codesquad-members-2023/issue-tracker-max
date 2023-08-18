import { Theme, css } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';
import { useTheme } from '@emotion/react';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
import { formatISODateString, getFormattedTimeDifference } from '@utils/time';
type Props = {
  status?: string;
  createdAt: string;
  author: User;
  comments?: CommentType[];
};

export const PostInformationHeaderMeta: React.FC<Props> = ({
  status,
  createdAt,
  author,
  comments,
}: Props) => {
  const theme = useTheme() as any;
  const statusText = status === 'open' ? '열린' : '닫힌';
  const statusResultText = status === 'open' ? '열렸' : '닫혔';

  const diff = Date.now() - new Date(createdAt).getTime();
  const date =
    diff < FIVE_DAYS_IN_MS
      ? getFormattedTimeDifference(createdAt)
      : formatISODateString(createdAt);

  return (
    <>
      <div css={headerMetaStyle}>
        <InformationTag
          size="L"
          typeVariant="filled"
          fillColor={
            status === 'open' ? theme.palette.blue : theme.neutral.text.weak
          }
          textColor="light"
        >
          <AlertCircle stroke={theme.brand.text.default} />
          {statusText}이슈
        </InformationTag>
        <span>
          이 이슈가 {date}에 {author.loginId}님에 의해 {statusResultText}
          습니다
        </span>
        <span>∙</span>
        <span>
          코멘트
          {comments?.length}개
        </span>
      </div>
    </>
  );
};

const headerMetaStyle = (theme: Theme) => css`
  display: flex;
  gap: 8px;
  align-items: center;

  & span {
    color: ${theme.neutral.text.weak};
    font: ${theme.fonts.displayMedium16};
  }
`;

const FIVE_DAYS_IN_MS = 432000000;
