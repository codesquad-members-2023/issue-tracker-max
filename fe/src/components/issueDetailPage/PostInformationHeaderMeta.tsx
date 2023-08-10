import { InformationTag } from '@components/common/InformationTag';
import { useTheme } from '@emotion/react';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
type Props = {
  status?: string;
  createdAt?: string;
  author?: string;
};

export const PostInformationHeaderMeta: React.FC<Props> = ({
  status = 'open',
  createdAt = '1시간',
  author = 'blue',
}: Props) => {
  const theme = useTheme() as any;
  const statusText = status === 'open' ? '열린' : '닫힌';
  const statusResultText = status === 'open' ? '열렸' : '닫혔';

  return (
    <>
      <div
        css={{
          display: 'flex',
          gap: '8px',
          alignItems: 'center',

          '& span': {
            color: theme.neutral.text.weak,
            font: theme.fonts.displayMedium16,
          },
        }}
      >
        <InformationTag
          size="L"
          typeVariant="filled"
          fillColor={theme.palette.blue}
          textColor="light"
        >
          <AlertCircle stroke={theme.brand.text.default} />
          {statusText}이슈
        </InformationTag>
        <span>
          이 이슈가 {createdAt}전에 {author}님에 의해 {statusResultText}습니다
        </span>
        <span>∙</span>
        <span>
          코멘트
          {'2'}개
        </span>
      </div>
    </>
  );
};
