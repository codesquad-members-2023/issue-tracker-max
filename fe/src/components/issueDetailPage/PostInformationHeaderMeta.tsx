// import { InformationTag } from '@components/common/InformationTag';
// import { useTheme } from '@emotion/react';
// import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
// type Props = {
//   status: string;
// };

// export const PostInformationHeaderMeta: React.FC<Props> = ({
//   status,
// }: Props) => {
//   const theme = useTheme() as any;
//   return (
//     <>
//       <div
//         css={{
//           display: 'flex',
//           gap: '8px',
//         }}
//       >
//         <InformationTag
//           size="L"
//           typeVariant="filled"
//           fillColor={theme.palette.blue}
//           textColor="light"
//         >
//           <AlertCircle stroke={theme.brand.text.default} />
//           {issueStatusText}이슈
//         </InformationTag>
//         <span>
//           이 이슈가 {createdAt}전에 {author}님에 의해 {`열렸습니다/닫혔습니다`}
//         </span>
//       </div>
//     </>
//   );
// };
