    /**
     * 设置文件访问权限
     *
     * @param file 文件类
     * @throws IOException io异常
     */
    private static void nioAssignment(File file) throws IOException {
        boolean supportPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
        if (supportPosix) {
            Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            Path path = Paths.get(file.getAbsolutePath());
            Files.setPosixFilePermissions(path, perms);
            // 当是目录的时候，递归设置文件权限
            if (Files.isDirectory(path)) {
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
                    for (Path subPath : ds) {
                        nioAssignment(subPath.toFile());
                    }
                } catch (IOException e) {
                    logger.error(UploadFileUtil.class.getName(), e);
                }
            }
        }
    }


public static ArrayList<HashMap<String, String>> upload(MultipartFile[] request, OpticsMtfFaFileEntity opticsMtfFaFile) throws Exception {
        ArrayList filename = new ArrayList();
        for (MultipartFile multipartFile : request) {
            if (multipartFile.getSize() > 3145728) {
                throw new Exception("文件大于3M");
            }
            if (multipartFile != null) {
                StringBuffer sb = new StringBuffer();
                String systemStr = System.getProperty("os.name");
                if (systemStr.contains("Linux")) {
                    sb.append(OpticsInteractionAnalysisString.getFileUploadRootPath());
                } else {
                    sb.append(OpticsInteractionAnalysisString.getFileUploadRootPathWin());
                }
                sb.append(opticsMtfFaFile.getTypeId());
                sb.append("/");
                sb.append(opticsMtfFaFile.getSiteId());
                sb.append("/");
                sb.append(opticsMtfFaFile.getMachineNo());
                sb.append("/");
                sb.append(opticsMtfFaFile.getProject());
                sb.append("/");
                sb.append(opticsMtfFaFile.getPair());
                sb.append("/");
                sb.append(opticsMtfFaFile.getMtfDate());
                File fileDir = new File(sb.toString());
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                if (systemStr.contains("Linux")) {
                    nioAssignment(fileDir);
                }
                sb.append("/");
                sb.append(opticsMtfFaFile.getMtfShift());
                sb.append(System.currentTimeMillis());
                sb.append(".");
                String[] sp;
                if (multipartFile.getOriginalFilename() != null) {
                    sp = multipartFile.getOriginalFilename().split("\\.");
                } else {
                    throw new Exception("文件名不规范");
                }

                if (sp != null && sp.length >= 2) {
                    sb.append(multipartFile.getOriginalFilename().split("\\.")[sp.length - 1]);
                } else {
                    throw new Exception("文件名不规范");
                }

                String path = sb.toString();
                HashMap<String, String> fileInfo = new HashMap<>(3);
                fileInfo.put("fileName", multipartFile.getOriginalFilename());
                fileInfo.put("fileSize", String.valueOf(multipartFile.getSize()));
                File fileImg = new File(path);
                multipartFile.transferTo(fileImg);
                if (systemStr.contains("Linux")) {
                    nioAssignment(fileImg);
                }
                fileInfo.put("path", path.replace(OpticsInteractionAnalysisString.getFileUploadRootPath(), OpticsInteractionAnalysisString.getFileUrl()));
                filename.add(fileInfo);
            }
        }
        return filename;
    }
